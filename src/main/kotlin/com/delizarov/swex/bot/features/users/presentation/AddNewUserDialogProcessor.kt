package com.delizarov.swex.bot.features.users.presentation

import com.delizarov.swex.bot.features.users.domain.interactors.UsersInteractor
import com.delizarov.swex.bot.features.users.domain.model.UserName
import com.delizarov.swex.bot.resources.Resources
import com.delizarov.swex.bot.resources.ResourcesImpl
import com.delizarov.swex.bot.system.dialog.DialogProcessor
import com.delizarov.swex.bot.system.dialog.reactions.BotReaction
import com.delizarov.swex.bot.system.dialog.reactions.BotReactionFactory
import com.delizarov.swex.utils.chatId
import com.delizarov.swex.utils.isMention
import com.delizarov.swex.utils.isNegative
import com.delizarov.swex.utils.isPositive
import com.github.kotlintelegrambot.entities.CallbackQuery
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.Message

private val yesNoOptions = listOf(
    ResourcesImpl.yes to "Yes",
    ResourcesImpl.no to "No"
)

private sealed interface Step {
    object Start: Step
    object InputContact: Step
    class ConfirmInput(val users: List<UserName>): Step
    object End: Step
}

class AddNewUserDialogProcessor(
    private val interactor: UsersInteractor,
    private val reactions: BotReactionFactory,
    private val resources: Resources,
) : DialogProcessor {

    private var step: Step = Step.Start

    override fun processMessage(lastMessage: Message, history: List<Message>): BotReaction {
        return when (step) {
            is Step.Start -> processStartDialog(lastMessage)
            is Step.InputContact -> processInputContact(lastMessage)
            is Step.ConfirmInput -> processConfirmationMessage(lastMessage)
            is Step.End -> processEnd()
        }
    }

    override fun processInput(callbackQuery: CallbackQuery): BotReaction {
        return when (step) {
            is Step.ConfirmInput -> processConfirmationInput(callbackQuery)
            else -> doNothing()
        }
    }

    private fun processStartDialog(userMessage: Message): BotReaction {
        val chatId = userMessage.chatId

        return moveNext(
            Step.InputContact
        ) { reactions.answer(chatId, resources.whomToAddQuestion) }
    }

    private fun processInputContact(userMessage: Message): BotReaction {
        val chatId = userMessage.chatId

        val mentions = parseMentions(userMessage)

        return if (mentions.isEmpty()) {
            moveNext(Step.InputContact) { reactions.answer(chatId, resources.youNeedToPassAContact) }
        } else {
            moveNext(Step.ConfirmInput(mentions)) {
                reactions.keyboardAnswer(
                    chatId = chatId,
                    text = resources.theseUsersWillBeAdded(mentions),
                    options = yesNoOptions
                )
            }
        }
    }

    private fun processConfirmationInput(callbackQuery: CallbackQuery): BotReaction {
        val chatId = callbackQuery.message?.chatId ?: return doNothing()

        return when {
            callbackQuery.data.isPositive -> tryAddNewUsers(chatId)
            else -> moveNext(Step.InputContact) {
                reactions.answer(chatId, resources.whomToAddQuestion)
            }
        }
    }

    private fun processConfirmationMessage(message: Message): BotReaction {
        val chatId = message.chatId
        return when {
            message.text?.isPositive == true -> tryAddNewUsers(chatId)
            message.text?.isNegative == true -> moveNext(Step.InputContact) {
                reactions.answer(chatId, resources.whomToAddQuestion)
            }

            else -> doNothing()
        }
    }

    private fun tryAddNewUsers(chatId: ChatId): BotReaction {
        val users = (step as Step.ConfirmInput).users
        val rowIds = interactor.addNewUsers(users)

        val addedUsers = rowIds
            .mapIndexed { i, id -> i to id }
            .filter { pair ->
                pair.second != -1L
            }
            .map { users[it.first] }

        val notAddedUsers = rowIds
            .mapIndexed { i, id -> i to id }
            .filter { pair ->
                pair.second == -1L
            }
            .map { users[it.first] }

        val addedUsersText = resources
            .theseUsersWereAdded(addedUsers)
            .takeIf { addedUsers.isNotEmpty() }
            ?: ""

        val notAddedUsersText = resources
            .theseUsersWerentAdded(notAddedUsers)
            .takeIf { notAddedUsers.isNotEmpty() }
            ?: ""

        return moveNext(Step.End) {
            reactions.answer(chatId, addedUsersText + notAddedUsersText)
        }
    }

    private fun processEnd(): BotReaction {
        return moveNext(Step.End) { reactions.doNothing() }
    }

    private fun moveNext(next: Step, reactionCreator: () -> BotReaction): BotReaction {
        step = next

        return reactionCreator.invoke()
    }

    private fun doNothing() = moveNext(step) { reactions.doNothing() }

    private fun parseMentions(message: Message): List<String> {
        val text = message.text ?: return emptyList()

        return message.entities
            ?.filter { it.isMention }
            ?.map {
                val start = it.offset
                val end = it.offset + it.length
                text.substring(start, end)
            } ?: emptyList()

    }
}
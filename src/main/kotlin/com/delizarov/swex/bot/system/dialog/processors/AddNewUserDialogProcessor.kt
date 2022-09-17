package com.delizarov.swex.bot.system.dialog.processors

import com.delizarov.swex.bot.domain.interactors.UsersInteractor
import com.delizarov.swex.bot.resources.Resources
import com.delizarov.swex.bot.system.dialog.DialogProcessor
import com.delizarov.swex.bot.system.dialog.reactions.MessageReaction
import com.delizarov.swex.bot.system.dialog.reactions.MessageReactionFactory
import com.delizarov.swex.logger.Logger
import com.delizarov.swex.logger.LoggerImpl
import com.delizarov.swex.utils.chatId
import com.delizarov.swex.utils.isMention
import com.delizarov.swex.utils.isNegative
import com.delizarov.swex.utils.isPositive
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.Message
import com.github.kotlintelegrambot.entities.User

private val yesNoOptions = listOf(
    "Да" to "Yes",
    "Нет" to "No",
)

private sealed interface Step {
    object Start : Step
    object InputContact : Step
    class ConfirmInput(val users2Add: List<User>) : Step
    object Stop : Step
}

class AddNewUserDialogProcessor(
    private val interactor: UsersInteractor,
    private val reactions: MessageReactionFactory,
    private val resources: Resources,
) : DialogProcessor {

    private var step: Step = Step.Start

    override fun process(lastMessage: Message, history: List<Message>): MessageReaction {
        return when (step) {
            is Step.Start -> processStartDialog(lastMessage)
            is Step.InputContact -> processInputContact(lastMessage)
            is Step.ConfirmInput -> processConfirmation(lastMessage)
            is Step.Stop -> processStop()
        }
    }

    private fun processStartDialog(userMessage: Message): MessageReaction {
        val chatId = userMessage.chatId

        return moveNext(
            Step.InputContact
        ) { reactions.answer(chatId, resources.whomToAddQuestion) }
    }

    private fun processInputContact(userMessage: Message): MessageReaction {
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

    private fun processConfirmation(userMessage: Message): MessageReaction {
        val chatId = userMessage.chatId
        return when {
            userMessage.text?.isPositive == true -> tryAddNewUsers(chatId)
            userMessage.text?.isNegative == true -> moveNext(Step.InputContact) {
                reactions.answer(chatId, resources.whomToAddQuestion)
            }
            else -> doNothing()
        }
    }

    private fun tryAddNewUsers(chatId: ChatId): MessageReaction {
        val users = (step as Step.ConfirmInput).users2Add
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

        return moveNext(Step.Stop) {
            reactions.answer(chatId, addedUsersText + notAddedUsersText)
        }
    }

    private fun processStop(): MessageReaction {
        return moveNext(Step.Stop) { reactions.doNothing() }
    }

    private fun moveNext(next: Step, reactionCreator: () -> MessageReaction): MessageReaction {
        step = next

        return reactionCreator.invoke()
    }

    private fun doNothing() = moveNext(step) { reactions.doNothing() }

    private fun parseMentions(message: Message): List<String> {
        val text = message.text ?: return emptyList()

        return message.entities
            ?.filter { it.isMention }
            ?.map { text.substring(it.offset, it.) }

    }
}

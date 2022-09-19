package com.delizarov.swex.bot.features.users.presentation

import com.delizarov.swex.bot.features.users.domain.interactors.UsersInteractor
import com.delizarov.swex.bot.features.users.domain.model.UserName
import com.delizarov.swex.bot.resources.Resources
import com.delizarov.swex.bot.system.dialog.DialogProcessor
import com.delizarov.swex.bot.system.dialog.reactions.BotReaction
import com.delizarov.swex.bot.system.dialog.reactions.BotReactionFactory
import com.delizarov.swex.utils.chatId
import com.github.kotlintelegrambot.entities.Message

class FindUserDialogProcessor(
    private val interactor: UsersInteractor,
    private val resources: Resources,
    private val reactions: BotReactionFactory
) : DialogProcessor {

    override fun processMessage(lastMessage: Message, history: List<Message>): BotReaction {
        val chatId = lastMessage.chatId
        val users2Find = parseUsernames(lastMessage)

        return if (users2Find.isNotEmpty()) {
            val searchResults = interactor.findUsers(users2Find)
            val answer = resources.usersFoundAnswer(searchResults, resources)

            reactions.answer(chatId, answer)
        } else {


        }


    }

    private fun parseUsernames(message: Message): List<UserName> {

        return emptyList()
    }
}
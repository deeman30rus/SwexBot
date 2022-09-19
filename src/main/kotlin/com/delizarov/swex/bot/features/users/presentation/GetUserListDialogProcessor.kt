package com.delizarov.swex.bot.features.users.presentation

import com.delizarov.swex.bot.features.users.domain.interactors.UsersInteractor
import com.delizarov.swex.bot.resources.Resources
import com.delizarov.swex.bot.system.dialog.DialogProcessor
import com.delizarov.swex.bot.system.dialog.reactions.BotReaction
import com.delizarov.swex.bot.system.dialog.reactions.BotReactionFactory
import com.delizarov.swex.utils.chatId
import com.github.kotlintelegrambot.entities.Message

class GetUserListDialogProcessor(
    private val interactor: UsersInteractor,
    private val resources: Resources,
    private val reactions: BotReactionFactory
): DialogProcessor {

    override fun processMessage(lastMessage: Message, history: List<Message>): BotReaction {
        val chatId = lastMessage.chatId
        val users = interactor.getAllUsers()
        val text = resources.usersAdded(users)

        return reactions.answer(chatId, text)
    }
}
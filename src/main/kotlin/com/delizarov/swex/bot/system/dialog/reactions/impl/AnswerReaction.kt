package com.delizarov.swex.bot.system.dialog.reactions.impl

import com.delizarov.swex.bot.system.dialog.reactions.MessageReaction
import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId

class AnswerReaction(
    private val chatId: ChatId,
    private val text: String,
): MessageReaction {

    override fun react(bot: Bot) {
        bot.sendMessage(chatId, text)
    }
}
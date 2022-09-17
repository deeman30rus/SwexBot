package com.delizarov.swex.bot.system.dialog.reactions.impl

import com.delizarov.swex.bot.system.dialog.reactions.MessageReaction
import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton

class KeyboardOptionsReaction(
    private val chatId: ChatId,
    private val text: String,
    private val options: List<Pair<String, String>>,
): MessageReaction {

    override fun react(bot: Bot) {
        val buttonsRow = options.map {
            listOf(InlineKeyboardButton.CallbackData(text = it.first, callbackData = it.second))
        }.toTypedArray()

        val inlineKeyboardMarkup = InlineKeyboardMarkup.create(*buttonsRow)

        bot.sendMessage(
            chatId = chatId,
            text = text,
            replyMarkup = inlineKeyboardMarkup
        )
    }
}
package com.delizarov.swex.bot.system.dialog.reactions

import com.delizarov.swex.bot.system.dialog.reactions.impl.AnswerReaction
import com.delizarov.swex.bot.system.dialog.reactions.impl.DoNothingReaction
import com.delizarov.swex.bot.system.dialog.reactions.impl.KeyboardOptionsReaction
import com.github.kotlintelegrambot.entities.ChatId

class MessageReactionFactory {

    fun doNothing(): MessageReaction {
        return DoNothingReaction()
    }

    fun answer(chatId: ChatId, text: String): MessageReaction {
        return AnswerReaction(chatId, text)
    }

    fun keyboardAnswer(chatId: ChatId, text: String, options: List<Pair<String, String>>): MessageReaction {
        return KeyboardOptionsReaction(chatId, text, options)
    }
}
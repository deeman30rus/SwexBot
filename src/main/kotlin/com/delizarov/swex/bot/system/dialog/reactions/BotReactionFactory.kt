package com.delizarov.swex.bot.system.dialog.reactions

import com.delizarov.swex.bot.system.dialog.reactions.impl.AnswerReaction
import com.delizarov.swex.bot.system.dialog.reactions.impl.DoNothingReaction
import com.delizarov.swex.bot.system.dialog.reactions.impl.KeyboardOptionsReaction
import com.github.kotlintelegrambot.entities.ChatId

class BotReactionFactory {

    fun doNothing(): BotReaction {
        return DoNothingReaction()
    }

    fun answer(chatId: ChatId, text: String): BotReaction {
        return AnswerReaction(chatId, text)
    }

    fun keyboardAnswer(chatId: ChatId, text: String, options: List<Pair<String, String>>): BotReaction {
        return KeyboardOptionsReaction(chatId, text, options)
    }
}
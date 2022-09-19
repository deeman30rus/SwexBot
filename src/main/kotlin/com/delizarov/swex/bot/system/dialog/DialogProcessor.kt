package com.delizarov.swex.bot.system.dialog

import com.delizarov.swex.bot.system.dialog.reactions.BotReaction
import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.CallbackQuery
import com.github.kotlintelegrambot.entities.Message

interface DialogProcessor {

    fun processMessage(lastMessage: Message, history: List<Message>): BotReaction {
        return BotReaction.Null
    }

    fun processInput(callbackQuery: CallbackQuery): BotReaction {
        return BotReaction.Null
    }
}
package com.delizarov.swex.bot.system.dialog

import com.delizarov.swex.bot.system.dialog.reactions.MessageReaction
import com.github.kotlintelegrambot.entities.Message

interface DialogProcessor {

    fun process(lastMessage: Message, history: List<Message>): MessageReaction
}
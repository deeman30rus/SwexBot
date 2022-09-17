package com.delizarov.swex.utils

import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.Message

val Message.chatId: ChatId
    get() = ChatId.fromId(chat.id)
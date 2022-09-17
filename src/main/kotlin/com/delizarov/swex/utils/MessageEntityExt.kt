package com.delizarov.swex.utils

import com.github.kotlintelegrambot.entities.MessageEntity

val MessageEntity.isMention: Boolean
    get() = type == MessageEntity.Type.MENTION
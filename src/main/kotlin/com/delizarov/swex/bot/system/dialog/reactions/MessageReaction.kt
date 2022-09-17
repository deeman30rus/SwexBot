package com.delizarov.swex.bot.system.dialog.reactions

import com.github.kotlintelegrambot.Bot

interface MessageReaction {

    fun react(bot: Bot)
}
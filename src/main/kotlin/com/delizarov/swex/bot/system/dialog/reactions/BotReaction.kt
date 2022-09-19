package com.delizarov.swex.bot.system.dialog.reactions

import com.github.kotlintelegrambot.Bot

interface BotReaction {

    fun react(bot: Bot)

    object Null: BotReaction {

        override fun react(bot: Bot) = Unit
    }
}
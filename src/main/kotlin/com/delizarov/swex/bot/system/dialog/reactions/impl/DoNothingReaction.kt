package com.delizarov.swex.bot.system.dialog.reactions.impl

import com.delizarov.swex.bot.system.dialog.reactions.BotReaction
import com.github.kotlintelegrambot.Bot

class DoNothingReaction: BotReaction {

    override fun react(bot: Bot) = Unit
}
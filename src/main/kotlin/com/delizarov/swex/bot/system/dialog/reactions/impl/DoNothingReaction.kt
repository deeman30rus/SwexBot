package com.delizarov.swex.bot.system.dialog.reactions.impl

import com.delizarov.swex.bot.system.dialog.reactions.MessageReaction
import com.github.kotlintelegrambot.Bot

class DoNothingReaction: MessageReaction {

    override fun react(bot: Bot) = Unit
}
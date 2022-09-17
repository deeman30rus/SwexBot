package com.delizarov.swex.bot.system.pipeline

import com.delizarov.swex.assertions.assertFalse
import com.delizarov.swex.bot.system.dialog.DialogProcessor
import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.dispatcher.handlers.CommandHandlerEnvironment
import com.github.kotlintelegrambot.dispatcher.handlers.ContactHandlerEnvironment
import com.github.kotlintelegrambot.dispatcher.handlers.TextHandlerEnvironment
import com.github.kotlintelegrambot.entities.Message

class Pipeline(
    private val processor: DialogProcessor,
) {

    var _isClosed = false
    val isClosed: Boolean
        get() = _isClosed

    private val history: MutableList<Message> = mutableListOf()
    private var _bot: Bot? = null
    private val bot: Bot
        get() = requireNotNull(_bot)

    fun proceed(env: CommandHandlerEnvironment) {
        assertFalse(isClosed)

        this._bot = env.bot
        val message = env.message
        history.add(env.message)

        processMessage(message)
    }

    fun proceed(env: ContactHandlerEnvironment) {
        assertFalse(isClosed)

        this._bot = env.bot
        val message = env.message
        history.add(env.message)

        processMessage(message)
    }

    fun proceed(env: TextHandlerEnvironment) {
        assertFalse(isClosed)

        this._bot = env.bot
        val message = env.message
        history.add(env.message)

        processMessage(message)
    }

    private fun processMessage(message: Message) {
        val reaction = processor.process(message, history)

        reaction.react(bot)
    }

    fun close() {
        this._bot = null
        history.clear()
        _isClosed = true
    }
}
package com.delizarov.swex.bot

import com.delizarov.swex.bot.data.repository.AdminRepositoryImpl
import com.delizarov.swex.bot.data.repository.UsersRepositoryImpl
import com.delizarov.swex.bot.data.storage.MemoryUsersStorage
import com.delizarov.swex.bot.system.CommandAuthorizer
import com.delizarov.swex.bot.system.SupportedCommand
import com.delizarov.swex.bot.system.pipeline.PipelineFactory
import com.delizarov.swex.bot.system.pipeline.PipelinesHolder
import com.delizarov.swex.logger.Logger
import com.delizarov.swex.logger.LoggerImpl
import com.delizarov.swex.utils.chatId
import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.contact
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.entities.ChatId

private const val API_KEY = "5679131501:AAE8tVcpwTwRwyqO5kjmBMzSTgORGh6kcYI"

object BotApp {

    private lateinit var swexBot: Bot

    private val logger: Logger = LoggerImpl

    private val pipelineFactory = PipelineFactory(
        usersRepository = UsersRepositoryImpl(
            storage = MemoryUsersStorage(),
        )
    )
    private val pipelinesHolder = PipelinesHolder()
    private val commandAuthorizer = CommandAuthorizer(
        adminRepository = AdminRepositoryImpl()
    )

    fun init() {
        swexBot = bot {
            token = API_KEY

            dispatch {
                contact {
                    val env = this
                    val chatId = ChatId.fromId(message.chat.id)

                    pipelinesHolder[chatId]?.let { pipeline ->
                        if (!pipeline.isClosed) {
                            pipeline.proceed(env)
                        }
                    }
                }
                text {
                    val env = this
                    val chatId = ChatId.fromId(message.chat.id)

                    pipelinesHolder[chatId]?.let { pipeline ->
                        if (!pipeline.isClosed) {
                            pipeline.proceed(env)
                        }
                    }
                }
                command("newUser") {
                    val env = this
                    val chatId = ChatId.fromId(message.chat.id)

                    pipelinesHolder[chatId]?.close()
                    pipelinesHolder.remove(chatId)

                    if (commandAuthorizer.isUserAuthorizedForAction(SupportedCommand.AddNewUser, chatId)) {
                        pipelineFactory.createAddNewUserPipeline().also { pipeline ->
                            pipelinesHolder[chatId] = pipeline
                            pipeline.proceed(env)
                        }

                        logger.info { "User id ${chatId.id} initiated add new user dialog" }
                    } else {
                        logger.warning { "User id ${chatId.id} tried add new user command" }
                    }
                }
                command("stop") {
                    val env = this
                    val chatId = message.chatId

                    pipelinesHolder[chatId]?.close()
                    pipelinesHolder.remove(chatId)
                }
            }
        }
    }

    fun run() {
        swexBot.startPolling()
    }
}
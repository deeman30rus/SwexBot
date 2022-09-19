package com.delizarov.swex.bot

import com.delizarov.swex.SystemComponent
import com.delizarov.swex.bot.di.BotComponent
import com.delizarov.swex.bot.features.admins.di.AdminComponent
import com.delizarov.swex.bot.features.users.di.UsersComponent
import com.delizarov.swex.bot.resources.ResourcesImpl
import com.delizarov.swex.bot.system.CommandAuthorizer
import com.delizarov.swex.bot.system.SupportedCommand
import com.delizarov.swex.bot.system.dialog.reactions.BotReactionFactory
import com.delizarov.swex.bot.system.pipeline.PipelineFactory
import com.delizarov.swex.bot.system.pipeline.PipelinesHolder
import com.delizarov.swex.utils.chatId
import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.callbackQuery
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.contact
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.entities.ChatId

private const val API_KEY = "5679131501:AAE8tVcpwTwRwyqO5kjmBMzSTgORGh6kcYI"

object BotApp {

    private lateinit var swexBot: Bot
    private lateinit var botComponent: BotComponent

    private val pipelineFactory: PipelineFactory
        get() = botComponent.pipelineFactory

    private val pipelinesHolder: PipelinesHolder
        get() = botComponent.pipelinesHolder

    private val commandAuthorizer: CommandAuthorizer
        get() = botComponent.commandAuthrizer

    fun init() {
        val usersComponent = UsersComponent.Builder().build()
        val adminComponent = AdminComponent.Builder().build()
        val systemComponent = SystemComponent.Builder().build()

        val dependencies = BotComponent.Dependencies(
            usersInteractor = usersComponent.usersInteractor,
            adminRepository = adminComponent.adminRepository,
            botReactionsFactory = BotReactionFactory(),
            resources = ResourcesImpl,
        )

        botComponent = BotComponent.Factory().create(dependencies)

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
                callbackQuery {
                    val env = this
                    val chatId = callbackQuery.message?.chatId ?: return@callbackQuery

                    pipelinesHolder[chatId]?.let { pipeline ->
                        if (!pipeline.isClosed) {
                            pipeline.proceed(env)
                        }
                    }
                }
                command("newUser") {
                    val env = this
                    val chatId = message.chatId

                    pipelinesHolder[chatId]?.close()
                    pipelinesHolder.remove(chatId)

                    if (commandAuthorizer.isUserAuthorizedForAction(SupportedCommand.AddNewUser, chatId)) {
                        pipelineFactory.createAddNewUserPipeline().also { pipeline ->
                            pipelinesHolder[chatId] = pipeline
                            pipeline.proceed(env)
                        }
                    }
                }
                command("usersList") {
                    val env = this
                    val chatId = message.chatId

                    if (commandAuthorizer.isUserAuthorizedForAction(SupportedCommand.GetUserList, chatId)) {
                        pipelineFactory.createGetUsersListPipeline().also { pipeline ->
                            pipelinesHolder[chatId] = pipeline
                            pipeline.proceed(env)
                        }
                    }
                }
                command("findUser") {
                    val env = this
                    val chatId = message.chatId

                    if (commandAuthorizer.isUserAuthorizedForAction(SupportedCommand.GetUserList, chatId)) {
                        pipelineFactory.createFindUserPipeline().also { pipeline ->
                            pipelinesHolder[chatId] = pipeline
                            pipeline.proceed(env)
                        }
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
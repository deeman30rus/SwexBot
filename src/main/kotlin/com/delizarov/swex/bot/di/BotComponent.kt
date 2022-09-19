package com.delizarov.swex.bot.di

import com.delizarov.swex.bot.features.users.domain.interactors.UsersInteractor
import com.delizarov.swex.bot.features.admins.domain.repositories.AdminRepository
import com.delizarov.swex.bot.resources.Resources
import com.delizarov.swex.bot.system.CommandAuthorizer
import com.delizarov.swex.bot.system.dialog.reactions.BotReactionFactory
import com.delizarov.swex.bot.system.pipeline.PipelineFactory
import com.delizarov.swex.bot.system.pipeline.PipelinesHolder

interface BotComponent {

    val pipelineFactory: PipelineFactory
    val pipelinesHolder: PipelinesHolder
    val commandAuthrizer: CommandAuthorizer

    class Factory {

        fun create(dependencies: Dependencies): BotComponent {
            return BotComponentImpl(dependencies)
        }
    }

    class Dependencies(
        val usersInteractor: UsersInteractor,
        val adminRepository: AdminRepository,
        val botReactionsFactory: BotReactionFactory,
        val resources: Resources,
    )
}

private class BotComponentImpl(
    private val dependencies: BotComponent.Dependencies
): BotComponent {

    override val pipelineFactory: PipelineFactory by lazy { createPipelineFactory() }
    override val pipelinesHolder: PipelinesHolder by lazy { createPipelineHolder() }
    override val commandAuthrizer: CommandAuthorizer by lazy { createCommandAutohrizer() }

    private fun createPipelineFactory(): PipelineFactory {
        return PipelineFactory(
            usersInteractor = dependencies.usersInteractor,
            reactionsFactory = dependencies.botReactionsFactory,
            resources = dependencies.resources
        )
    }

    private fun createPipelineHolder(): PipelinesHolder {
        return PipelinesHolder()
    }

    private fun createCommandAutohrizer(): CommandAuthorizer {
        return CommandAuthorizer(
            dependencies.adminRepository
        )
    }
}
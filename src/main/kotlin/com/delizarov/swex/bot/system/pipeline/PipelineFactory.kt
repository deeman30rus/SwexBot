package com.delizarov.swex.bot.system.pipeline

import com.delizarov.swex.bot.features.users.domain.interactors.UsersInteractor
import com.delizarov.swex.bot.features.users.domain.repositories.UsersRepository
import com.delizarov.swex.bot.resources.ResourcesImpl
import com.delizarov.swex.bot.features.users.presentation.AddNewUserDialogProcessor
import com.delizarov.swex.bot.features.users.presentation.FindUserDialogProcessor
import com.delizarov.swex.bot.features.users.presentation.GetUserListDialogProcessor
import com.delizarov.swex.bot.resources.Resources
import com.delizarov.swex.bot.system.dialog.reactions.BotReactionFactory

class PipelineFactory(
    private val usersInteractor: UsersInteractor,
    private val reactionsFactory: BotReactionFactory,
    private val resources: Resources,
) {

    fun createAddNewUserPipeline(): Pipeline {
        val processor = AddNewUserDialogProcessor(
            interactor = usersInteractor,
            reactions = reactionsFactory,
            resources = resources,
        )

        return Pipeline(processor)
    }

    fun createGetUsersListPipeline(): Pipeline {
        val processor = GetUserListDialogProcessor(
            interactor = usersInteractor,
            reactions = reactionsFactory,
            resources = resources,
        )

        return Pipeline(processor)
    }

    fun createFindUserPipeline(): Pipeline {
        val processor = FindUserDialogProcessor(
            interactor = usersInteractor,
            reactions = reactionsFactory,
            resources = resources,
        )

        return Pipeline(processor)
    }
}
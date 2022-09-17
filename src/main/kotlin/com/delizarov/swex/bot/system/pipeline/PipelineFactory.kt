package com.delizarov.swex.bot.system.pipeline

import com.delizarov.swex.bot.domain.interactors.UsersInteractor
import com.delizarov.swex.bot.domain.repository.UsersRepository
import com.delizarov.swex.bot.resources.ResourcesImpl
import com.delizarov.swex.bot.system.dialog.processors.AddNewUserDialogProcessor
import com.delizarov.swex.bot.system.dialog.reactions.MessageReactionFactory

class PipelineFactory(
    private val usersRepository: UsersRepository,
) {

    fun createAddNewUserPipeline(): Pipeline {
        val processor = AddNewUserDialogProcessor(
            interactor = UsersInteractor(
                usersRepository = usersRepository,
            ),
            reactions = MessageReactionFactory(),
            resources = ResourcesImpl
        )

        return Pipeline(processor)
    }
}
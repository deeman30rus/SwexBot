package com.delizarov.swex.bot.features.users.di

import com.delizarov.swex.bot.features.users.data.repositories.UsersRepositoryImpl
import com.delizarov.swex.bot.features.users.data.storage.MemoryUsersStorage
import com.delizarov.swex.bot.features.users.data.storage.UsersStorage
import com.delizarov.swex.bot.features.users.domain.interactors.UsersInteractor
import com.delizarov.swex.bot.features.users.domain.repositories.UsersRepository

interface UsersComponent {

    val usersStorage: UsersStorage
    val usersRepository: UsersRepository
    val usersInteractor: UsersInteractor

    class Builder {

        fun build(): UsersComponent {

            return UsersComponentImpl()
        }
    }
}

private class UsersComponentImpl: UsersComponent {

    override val usersStorage: UsersStorage by lazy { createUsersStorage() }
    override val usersRepository: UsersRepository by lazy { createUsersRepository() }
    override val usersInteractor: UsersInteractor by lazy { createUsersInteractor() }

    private fun createUsersRepository(): UsersRepository {
        return UsersRepositoryImpl(usersStorage)
    }

    private fun createUsersStorage(): UsersStorage {
        return MemoryUsersStorage()
    }

    private fun createUsersInteractor(): UsersInteractor {
        return UsersInteractor(usersRepository)
    }

}
package com.delizarov.swex.bot.features.users.data.repositories

import com.delizarov.swex.bot.features.users.data.storage.UsersStorage
import com.delizarov.swex.bot.features.users.domain.model.SearchResult
import com.delizarov.swex.bot.features.users.domain.model.UserName
import com.delizarov.swex.bot.features.users.domain.repositories.UsersRepository

class UsersRepositoryImpl(
    private val storage: UsersStorage
) : UsersRepository {

    override fun addUser(user: UserName): Long {
        return storage.addUser(user)
    }

    override fun removeUser(user: UserName): Int {
        return storage.removeUser(user)
    }

    override fun getAllUser(): List<UserName> {
        return storage.getUsersList()
    }

    override fun findUsers(users: List<UserName>): List<SearchResult> {
        return users.map { user ->
            user to storage.findUser(user)
        }
    }
}
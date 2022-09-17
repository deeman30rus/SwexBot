package com.delizarov.swex.bot.data.repository

import com.delizarov.swex.bot.data.storage.UsersStorage
import com.delizarov.swex.bot.domain.repository.UsersRepository
import com.github.kotlintelegrambot.entities.User

class UsersRepositoryImpl(
    private val storage: UsersStorage
) : UsersRepository {

    override fun addUser(user: User): Long {
        return storage.addUser(user)
    }

    override fun removeUser(user: User): Int {
        return storage.removeUser(user)
    }

    override fun getUserList(): List<User> {
        return storage.getUsersList()
    }
}
package com.delizarov.swex.bot.data.storage

import com.github.kotlintelegrambot.entities.User


interface UsersStorage {

    fun addUser(user: User): Long

    fun removeUser(user: User): Int

    fun getUsersList(): List<User>
}

class MemoryUsersStorage: UsersStorage {

    private val users = mutableListOf<User>()

    override fun addUser(user: User): Long {
        users.add(user)

        return users.size.toLong()
    }

    override fun removeUser(user: User): Int {
        if (user in users) {
            users.remove(user)

            return 1
        }

        return 0
    }

    override fun getUsersList(): List<User> {
        return users
    }
}
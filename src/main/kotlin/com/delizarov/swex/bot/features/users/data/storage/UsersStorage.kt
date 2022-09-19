package com.delizarov.swex.bot.features.users.data.storage

import com.delizarov.swex.bot.features.users.domain.model.UserName

interface UsersStorage {

    fun addUser(user: UserName): Long

    fun removeUser(user: UserName): Int

    fun getUsersList(): List<UserName>

    fun findUser(user: UserName): Boolean
}

class MemoryUsersStorage: UsersStorage {

    private val users = mutableListOf<UserName>()

    override fun addUser(user: UserName): Long {
        users.add(user)

        return users.size.toLong()
    }

    override fun removeUser(user: UserName): Int {
        if (user in users) {
            users.remove(user)

            return 1
        }

        return 0
    }

    override fun getUsersList(): List<UserName> {
        return users
    }

    override fun findUser(user: UserName): Boolean {
        return user in users
    }
}
package com.delizarov.swex.bot.domain.repository

import com.github.kotlintelegrambot.entities.User

interface UsersRepository {

    fun addUser(user: User): Long

    fun removeUser(user: User): Int

    fun getUserList(): List<User>
}
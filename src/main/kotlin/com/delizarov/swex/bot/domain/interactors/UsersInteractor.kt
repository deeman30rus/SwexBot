package com.delizarov.swex.bot.domain.interactors

import com.delizarov.swex.bot.domain.repository.UsersRepository
import com.github.kotlintelegrambot.entities.User

class UsersInteractor(
    val usersRepository: UsersRepository,
) {

    fun addNewUsers(users: List<User>): List<Long> {
        return users.map { user ->
            usersRepository.addUser(user)
        }
    }
}
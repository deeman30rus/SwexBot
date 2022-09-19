package com.delizarov.swex.bot.features.users.domain.interactors

import com.delizarov.swex.bot.features.users.domain.model.SearchResult
import com.delizarov.swex.bot.features.users.domain.model.UserName
import com.delizarov.swex.bot.features.users.domain.repositories.UsersRepository

class UsersInteractor(
    private val usersRepository: UsersRepository,
) {

    fun addNewUsers(users: List<UserName>): List<Long> {
        return users.map { user ->
            usersRepository.addUser(user)
        }
    }

    fun getAllUsers(): List<UserName> {
        return usersRepository.getAllUser()
    }

    fun findUsers(users: List<UserName>): List<SearchResult> {
        return usersRepository.findUsers(users)
    }
}
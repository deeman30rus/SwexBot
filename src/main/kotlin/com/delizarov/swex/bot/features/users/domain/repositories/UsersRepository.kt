package com.delizarov.swex.bot.features.users.domain.repositories

import com.delizarov.swex.bot.features.users.domain.model.SearchResult
import com.delizarov.swex.bot.features.users.domain.model.UserName

interface UsersRepository {

    fun addUser(user: UserName): Long

    fun removeUser(user: UserName): Int

    fun getAllUser(): List<UserName>

    fun findUsers(users: List<UserName>): List<SearchResult>
}
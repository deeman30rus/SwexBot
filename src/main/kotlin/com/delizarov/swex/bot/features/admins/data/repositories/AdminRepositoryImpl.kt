package com.delizarov.swex.bot.features.admins.data.repositories

import com.delizarov.swex.bot.features.admins.domain.repositories.AdminRepository
import com.github.kotlintelegrambot.entities.ChatId

class AdminRepositoryImpl : AdminRepository {

    private val admins = setOf(
        228490718L
    ).map {
        ChatId.fromId(it)
    }

    override fun isUserAdmin(chatId: ChatId): Boolean {
        return chatId in admins
    }
}
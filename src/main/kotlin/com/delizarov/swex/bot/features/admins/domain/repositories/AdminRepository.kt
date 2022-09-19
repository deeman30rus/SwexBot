package com.delizarov.swex.bot.features.admins.domain.repositories

import com.github.kotlintelegrambot.entities.ChatId

interface AdminRepository {

    fun isUserAdmin(userId: ChatId): Boolean
}
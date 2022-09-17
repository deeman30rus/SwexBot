package com.delizarov.swex.bot.domain.repository

import com.github.kotlintelegrambot.entities.ChatId

interface AdminRepository {

    fun isUserAdmin(userId: ChatId): Boolean
}
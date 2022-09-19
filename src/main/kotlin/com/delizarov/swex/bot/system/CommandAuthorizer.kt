package com.delizarov.swex.bot.system

import com.delizarov.swex.bot.features.admins.domain.repositories.AdminRepository
import com.github.kotlintelegrambot.entities.ChatId

class CommandAuthorizer(
    private val adminRepository: AdminRepository,
) {

    fun isUserAuthorizedForAction(
        command: SupportedCommand,
        chatId: ChatId,
    ): Boolean {
        return when (command) {
            SupportedCommand.AddNewUser, SupportedCommand.GetUserList,
            SupportedCommand.RemoveUser, SupportedCommand.FindUser -> checkIfUserIsAdmin(chatId)
            else -> false
        }
    }

    private fun checkIfUserIsAdmin(chatId: ChatId): Boolean {
        return adminRepository.isUserAdmin(chatId)
    }
}
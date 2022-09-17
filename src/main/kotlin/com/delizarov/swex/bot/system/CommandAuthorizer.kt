package com.delizarov.swex.bot.system

import com.delizarov.swex.bot.domain.repository.AdminRepository
import com.github.kotlintelegrambot.dispatcher.handlers.CommandHandlerEnvironment
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
            SupportedCommand.RemoveUser -> checkIfUserIsAdmin(chatId)
            else -> false
        }
    }

    private fun checkIfUserIsAdmin(chatId: ChatId): Boolean {
        return adminRepository.isUserAdmin(chatId)
    }
}
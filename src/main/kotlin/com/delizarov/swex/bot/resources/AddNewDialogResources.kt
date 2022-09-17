package com.delizarov.swex.bot.resources

import com.github.kotlintelegrambot.entities.User

interface AddNewDialogResources {

    val whomToAddQuestion: String
        get() = "Кого хотите добавить?"

    val youNeedToPassAContact: String
        get() = "Отправьте пожалуйста контакт"

    val operationFailedTryAgainQuestion: String
        get() = "Ошибка записи, попробовать снова?"

    val usersSuccessfullyAdded: String
        get() = "Пользователь добавлен"

    fun theseUsersWillBeAdded(users: List<User>): String {
        return "Будут добавлены следующие пользователи:\n " + users.joinToString(separator = "\n") { "${it.firstName} ${it.lastName}" }
    }

    fun theseUsersWereAdded(users: List<User>): String {
        return "Были добавлены следующие пользователи:\n " + users.joinToString(separator = "\n") { "${it.firstName} ${it.lastName}" } + "\n"
    }

    fun theseUsersWerentAdded(users: List<User>): String {
        return "Не удалось добавить:\n " + users.joinToString(separator = "\n") { "${it.firstName} ${it.lastName}" }
    }
}
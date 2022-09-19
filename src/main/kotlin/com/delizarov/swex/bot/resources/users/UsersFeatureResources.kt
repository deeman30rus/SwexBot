package com.delizarov.swex.bot.resources.users

import com.delizarov.swex.bot.features.users.domain.model.UserName
import com.delizarov.swex.bot.resources.Resources
import com.delizarov.swex.utils.toYesNo

interface UsersFeatureResources {

    val whomToAddQuestion: String
        get() = "Кого хотите добавить?"

    val youNeedToPassAContact: String
        get() = "Отправьте пожалуйста контакт"

    val operationFailedTryAgainQuestion: String
        get() = "Ошибка записи, попробовать снова?"

    val usersSuccessfullyAdded: String
        get() = "Пользователь добавлен"

    fun theseUsersWillBeAdded(users: List<UserName>): String {
        return "Будут добавлены следующие пользователи:\n " + users.joinToString(separator = "\n") { it }
    }

    fun theseUsersWereAdded(users: List<UserName>): String {
        return "Были добавлены:\n " + users.joinToString(separator = "\n") { it } + "\n"
    }

    fun theseUsersWerentAdded(users: List<UserName>): String {
        return "Не удалось добавить:\n " + users.joinToString(separator = "\n") { it }
    }

    fun usersAdded(users: List<UserName>): String {
        return "Добавленные пользователи:\n" + users.joinToString(separator = "\n") { it }
    }

    fun usersFoundAnswer(answers: List<Pair<String, Boolean>>, resources: Resources): String {
        return answers.joinToString(separator = "\n") { "${it.first} ${it.second.toYesNo(resources)}" }
    }
}
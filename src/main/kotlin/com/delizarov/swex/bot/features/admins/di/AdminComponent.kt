package com.delizarov.swex.bot.features.admins.di

import com.delizarov.swex.bot.features.admins.data.repositories.AdminRepositoryImpl
import com.delizarov.swex.bot.features.admins.domain.repositories.AdminRepository

interface AdminComponent {

    val adminRepository: AdminRepository

    class Builder {

        fun build(): AdminComponent {

            return AdminComponentImpl()
        }
    }
}

private class AdminComponentImpl: AdminComponent {

    override val adminRepository: AdminRepository by lazy { createAdminRepository() }

    private fun createAdminRepository(): AdminRepository {
        return AdminRepositoryImpl()
    }
}
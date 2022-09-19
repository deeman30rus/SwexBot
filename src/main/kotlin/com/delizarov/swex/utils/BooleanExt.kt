package com.delizarov.swex.utils

import com.delizarov.swex.bot.resources.Resources

fun Boolean.toYesNo(resources: Resources): String {
    return if (this) resources.yes else resources.no
}
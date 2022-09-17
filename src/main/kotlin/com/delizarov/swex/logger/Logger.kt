package com.delizarov.swex.logger

interface Logger {

    fun info(msg: () -> String)

    fun warning(msg: () -> String)

    fun error(msg: () -> String)
}
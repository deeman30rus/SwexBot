package com.delizarov.swex.logger

import java.text.SimpleDateFormat
import java.util.*

enum class Level(val lvl: String) {
    Info("I"),
    Warning("W"),
    Error("E");
}

object LoggerImpl: Logger {

    private val dateFormatter = SimpleDateFormat("dd-M-yyyy hh:mm:ss:SSS")

    override fun info(msg: () -> String) {
        log(Level.Info, msg.invoke())
    }

    override fun warning(msg: () -> String) {
        log(Level.Warning, msg.invoke())
    }

    override fun error(msg: () -> String) {
        log(Level.Error, msg.invoke())
    }

    private fun log(level: Level, msg: String) {
        val date = dateFormatter.format(Date())

        println("$date ${level.lvl}: $msg")
    }
}
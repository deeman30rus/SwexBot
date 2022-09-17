package com.delizarov.swex.bot.system.pipeline

import com.github.kotlintelegrambot.entities.ChatId
import java.util.concurrent.ConcurrentHashMap

class PipelinesHolder {

    private val pipelines = ConcurrentHashMap<ChatId, Pipeline>()

    operator fun get(chatId: ChatId): Pipeline? {
        return pipelines[chatId]
    }

    operator fun set(chatId: ChatId, pipeline: Pipeline) {
        pipelines[chatId] = pipeline
    }

    fun remove(chatId: ChatId) {
        pipelines.remove(chatId)
    }
}
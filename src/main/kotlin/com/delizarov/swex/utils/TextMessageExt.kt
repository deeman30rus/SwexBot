package com.delizarov.swex.utils

private val positiveAnswers = listOf(
    "да",
    "yes"
)

private val negativeAnswers = listOf(
    "нет",
    "no"
)

val String.isPositive: Boolean
    get() {
        return this in positiveAnswers
    }

val String.isNegative: Boolean
    get() {
        return this in negativeAnswers
    }
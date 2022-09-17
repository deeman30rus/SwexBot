package com.delizarov.swex.assertions

object Assertions {

    var isEnabled: Boolean = true
}

fun assertTrue(condition: Boolean, lazyMsg: () -> String = { "" }) {
    assertFalse(!condition)
}

fun assertFalse(condition: Boolean, lazyMsg: () -> String = { "" }) {
    if (condition) {
        fail(lazyMsg)
    }
}

fun fail(lazyMsg: () -> String) {
    if (Assertions.isEnabled) {
        error(lazyMsg.invoke())
    }
}
package com.delizarov.swex

import com.delizarov.swex.logger.Logger
import com.delizarov.swex.logger.LoggerImpl

interface SystemComponent {

    val logger: Logger

    class Builder {

        fun build(): SystemComponent = SystemComponentImpl()
    }
}

private class SystemComponentImpl: SystemComponent {

    override val logger: Logger by lazy { LoggerImpl }
}


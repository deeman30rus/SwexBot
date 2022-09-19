package com.delizarov.swex.bot.resources

import com.delizarov.swex.bot.resources.users.UsersFeatureResources

interface Resources:
    CommonResources,
    UsersFeatureResources

object ResourcesImpl: Resources
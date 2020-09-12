package com.link184.products

import com.link184.architecture.mvvm.KoinApp
import com.link184.products.di.dataModelsModule
import com.link184.products.di.viewModule
import org.koin.core.logger.Level
import org.koin.core.module.Module

class This : KoinApp() {
    override val koinModules: List<Module> = dataModelsModule + viewModule
    override val logLevel: Level? = if (BuildConfig.DEBUG) Level.DEBUG else null
}
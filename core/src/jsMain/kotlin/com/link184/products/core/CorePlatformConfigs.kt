package com.link184.products.core

import io.ktor.client.engine.*
import io.ktor.client.engine.js.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

internal actual val timeout: Long = 30_000
internal actual val serializer: JsonSerializer = KotlinxSerializer(Json(JsonConfiguration(isLenient = true, ignoreUnknownKeys = true, useArrayPolymorphism = true, encodeDefaults = false)))
internal actual val logLevel: LogLevel = LogLevel.ALL
internal actual val userAgent: String = "chrome"
internal actual val clientType: ClientType = ClientType.BROWSER
internal actual val ioCoroutineDispatcher: CoroutineDispatcher = Dispatchers.Main

internal actual fun initClientEngine(session: Session): HttpClientEngineFactory<*> = Js
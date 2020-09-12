package com.link184.products.core

import io.ktor.client.engine.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import kotlinx.coroutines.CoroutineDispatcher

internal expect val timeout: Long
internal expect val serializer: JsonSerializer
internal expect val logLevel: LogLevel
internal expect val userAgent: String
internal expect val clientType: ClientType
internal expect val ioCoroutineDispatcher: CoroutineDispatcher

internal expect fun initClientEngine(session: Session): HttpClientEngineFactory<*>
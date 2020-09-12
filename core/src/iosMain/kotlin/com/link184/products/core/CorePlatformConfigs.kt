package com.link184.products.core

import io.ktor.client.engine.*
import io.ktor.client.engine.ios.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import platform.darwin.dispatch_queue_t
import kotlin.coroutines.CoroutineContext

internal actual val timeout: Long = 30_000
internal actual val serializer: JsonSerializer = KotlinxSerializer(Json(JsonConfiguration(isLenient = true, ignoreUnknownKeys = true, useArrayPolymorphism = true, encodeDefaults = false)))
internal actual val logLevel: LogLevel = LogLevel.ALL
internal actual val userAgent: String = "android"
internal actual val clientType: ClientType = ClientType.ANDROID
internal actual val ioCoroutineDispatcher: CoroutineDispatcher = NsQueueDispatcher(dispatch_get_main_queue())

internal class NsQueueDispatcher(private val dispatchQueue: dispatch_queue_t) : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatch_async(dispatchQueue.freeze()) {
            block.run()
        }
    }
}

internal actual fun initClientEngine(session: Session): HttpClientEngineFactory<*> = Ios
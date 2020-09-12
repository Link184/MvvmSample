package com.link184.products.core

import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.util.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

internal actual val timeout: Long = 30_000
internal actual val serializer: JsonSerializer = KotlinxSerializer(
    Json {
        ignoreUnknownKeys = false
        isLenient = true
        useArrayPolymorphism = true
        encodeDefaults = false
    }
)
internal actual val logLevel: LogLevel = LogLevel.ALL
internal actual val userAgent: String = "android"
internal actual val clientType: ClientType = ClientType.ANDROID
internal actual val ioCoroutineDispatcher: CoroutineDispatcher = Dispatchers.IO

@OptIn(InternalAPI::class)
internal actual fun initClientEngine(session: Session): HttpClientEngineFactory<*> = object : HttpClientEngineFactory<OkHttpConfig> {
    override fun create(block: OkHttpConfig.() -> Unit): HttpClientEngine {
        return OkHttpEngine(OkHttpConfig().apply(block).apply {
            config {
                followRedirects(true)
                connectTimeout(timeout, TimeUnit.MILLISECONDS)
                callTimeout(timeout * 2, TimeUnit.MILLISECONDS)
                readTimeout(timeout, TimeUnit.MILLISECONDS)
                writeTimeout(timeout * 2, TimeUnit.MILLISECONDS)
            }
            if (session.logsEnabled) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
        })
    }
}
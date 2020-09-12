package com.link184.products.core

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.utils.io.*


internal fun makeClient(session: Session): HttpClient = HttpClient(initClientEngine(session)) {
    install(JsonFeature) {
        serializer = com.link184.products.core.serializer
    }
    install(Logging) {
        logger = Logger.DEFAULT
        level = logLevel
    }
    install(UserAgent) {
        agent = userAgent
    }
    HttpResponseValidator {
        validateResponse(::responseValidator)
    }
    defaultRequest {
        val sessionUrl = URLBuilder(session.baseUrl)
        host = (sessionUrl.host + sessionUrl.encodedPath).removeSuffix("/")
        url.protocol = sessionUrl.protocol
        url.port = sessionUrl.port
    }
}

private suspend fun responseValidator(response: HttpResponse) {
    val body = response.content
    when (val code = response.status.value) {
        in 200..300 -> return
        in 300..599 -> {
            throw ServerResponseException(response)
        }
    }

    throw IllegalStateException("Invalid response code")
}

private suspend inline fun <reified T> ByteReadChannel.deserializeJson(): T {
    return serializer.read(typeInfo<T>(), readRemaining()) as T
}
package com.link184.products.core.service

import com.link184.products.core.Session
import com.link184.products.core.makeClient
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*

abstract class HttpService(session: Session) : BaseService<HttpClient> {
    override val client = makeClient(session)

    protected suspend inline fun <reified T> post(path: String, body: Any? = null, block: HttpRequestBuilder.() -> Unit = {}): T {
        return client.post(path = path) {
            maybeAddJsonBody(body)
            apply(block)
        }
    }

    protected suspend inline fun <reified T> get(path: String, body: Any? = null, block: HttpRequestBuilder.() -> Unit = {}): T {
        return client.get(path = path) {
            maybeAddJsonBody(body)
            apply(block)
        }
    }

    protected suspend inline fun <reified T> delete(path: String, body: Any? = null, block: HttpRequestBuilder.() -> Unit = {}): T {
        return client.delete(path = path) {
            maybeAddJsonBody(body)
            apply(block)
        }
    }

    protected fun HttpRequestBuilder.maybeAddJsonBody(body: Any?) {
        body?.let {
            this.body = defaultSerializer().write(body)
        }
    }
}
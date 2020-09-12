package com.link184.products.core

interface Session {
    val baseUrl: String

    /**
     * Base url of an media files storage. In our case with images
     */
    val mediaHostingBaseUrl: String
    val logsEnabled: Boolean
}
package com.link184.products.core

object TestSession : Session {
    override val baseUrl: String = "http://mobcategories.s3-website-eu-west-1.amazonaws.com"
    override val mediaHostingBaseUrl: String = "http://mobcategories.s3-website-eu-west-1.amazonaws.com"
    override val logsEnabled: Boolean = true
}
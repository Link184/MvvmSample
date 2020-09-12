package com.link184.products

import android.content.Context
import com.link184.products.core.PersistentSession
import com.link184.products.core.ProductsDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

class AndroidPersistentSession(context: Context) : PersistentSession {
    override val sqlDriver: SqlDriver = AndroidSqliteDriver(ProductsDatabase.Schema, context, "test.db")
    override val baseUrl: String = "http://mobcategories.s3-website-eu-west-1.amazonaws.com"
    override val mediaHostingBaseUrl: String = "http://mobcategories.s3-website-eu-west-1.amazonaws.com"
    override val logsEnabled: Boolean = BuildConfig.DEBUG
}
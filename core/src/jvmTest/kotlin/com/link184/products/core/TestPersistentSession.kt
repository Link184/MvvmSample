package com.link184.products.core

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver

class TestPersistentSession : PersistentSession {
    override val baseUrl: String = "ignored"
    override val mediaHostingBaseUrl: String = "ignored"
    override val logsEnabled: Boolean = true
    override val sqlDriver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).also {
        ProductsDatabase.Schema.create(it)
    }
}
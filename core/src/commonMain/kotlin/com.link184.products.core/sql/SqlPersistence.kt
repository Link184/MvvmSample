package com.link184.products.core.sql

import com.squareup.sqldelight.db.SqlDriver

interface SqlPersistence {
    val sqlDriver: SqlDriver
}
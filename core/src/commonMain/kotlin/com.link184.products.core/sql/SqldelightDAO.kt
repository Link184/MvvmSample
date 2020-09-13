package com.link184.products.core.sql

import com.link184.products.core.ProductsDatabase
import com.link184.products.core.model.Category
import com.link184.products.core.model.SalePrice
import com.squareup.sqldelight.ColumnAdapter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

abstract class SqldelightDAO(sqlPersistence: SqlPersistence) {
    val database = ProductsDatabase(sqlPersistence.sqlDriver,
            comlink184coreproducts.sql.CategorySqlModel.Adapter(
                    categoryAdapter = object : ColumnAdapter<Category, String> {
                        override fun decode(databaseValue: String): Category {
                            return Json.decodeFromString(databaseValue)
                        }

                        override fun encode(value: Category): String {
                            return Json.encodeToString(value)
                        }
                    }
            ),
        comlink184coreproducts.sql.ProductSqlModel.Adapter(
            object : ColumnAdapter<SalePrice, String> {
                override fun decode(databaseValue: String): SalePrice {
                    return Json.decodeFromString(databaseValue)
                }

                override fun encode(value: SalePrice): String {
                    return Json.encodeToString(value)
                }
            }
        )
    )
}
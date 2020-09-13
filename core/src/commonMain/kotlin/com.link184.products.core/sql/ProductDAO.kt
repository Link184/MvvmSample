package com.link184.products.core.sql

import com.link184.products.core.model.Product

class ProductDAO(sqlPersistence: SqlPersistence) : SqldelightDAO(sqlPersistence),
        SqlDelightCRUD<Product> {
    private val transacter = database.productSqlModelQueries

    override fun getItems(): List<Product> = transacter.selectAll(::Product).executeAsList()

    override fun insertItem(item: Product) {
        with(item) {
            transacter.insertItem(id, categoryId, name, url, description, salePrice)
        }
    }

    override fun insertAll(items: List<Product>) {
        transacter.transaction {
            items.forEach(this@ProductDAO::insertItem)
        }
    }

    override fun clear() = transacter.clear()

    fun selectByProductIdAndCategoryId(productId: String, categoryId: String): Product {
        return transacter.selectById(productId, categoryId, ::Product).executeAsOne()
    }
}
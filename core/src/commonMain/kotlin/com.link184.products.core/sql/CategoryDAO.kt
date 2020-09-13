package com.link184.products.core.sql

import com.link184.products.core.model.Category


class CategoryDAO(sqlPersistence: SqlPersistence) : SqldelightDAO(sqlPersistence),
    SqlDelightCRUD<Category> {
    private val transacter = database.categorySqlModelQueries
    private val productDAO = ProductDAO(sqlPersistence)

    override fun getItems() = transacter.selectAll().executeAsList()

    override fun insertItem(item: Category) {
        transacter.transaction {
            transacter.insertItem(item)
            item.products.forEach(productDAO::insertItem)
        }
    }

    override fun insertAll(items: List<Category>) {
        transacter.transaction {
            items.forEach(::insertItem)
        }
    }

    override fun clear() = transacter.clear()
}
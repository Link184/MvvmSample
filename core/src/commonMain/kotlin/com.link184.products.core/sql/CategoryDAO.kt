package com.link184.products.core.sql

import com.link184.products.core.model.Category


class CategoryDAO(sqlPersistence: SqlPersistence) : SqldelightDAO(sqlPersistence), SqlDelightCRUD<Category> {
    private val transacter = database.categorySqlModelQueries

    override fun getItems() = transacter.selectAll().executeAsList()

    override fun insertItem(item: Category) = transacter.insertItem(item)

    override fun insertAll(items: List<Category>) {
        transacter.transaction {
            items.forEach(transacter::insertItem)
        }
    }

    override fun clear() = transacter.clear()
}
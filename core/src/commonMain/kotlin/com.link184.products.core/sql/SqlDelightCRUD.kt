package com.link184.products.core.sql

interface SqlDelightCRUD<T> {
    fun getItems(): List<T>

    fun insertItem(item: T)

    fun insertAll(items: List<T>)

    fun replaceAll(items: List<T>) {
        clear()
        insertAll(items)
    }

    fun clear()
}
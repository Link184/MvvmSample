package com.link184.products.core.repository

import com.link184.products.core.sql.Entity
import com.link184.products.core.sql.SqlDelightCRUD
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow

abstract class BaseRepository<T>(val service: T) {
    open fun <Payload : Entity, E : List<Payload>, Model : E> exchangeCachedFlow(
        sqlDelightCRUD: SqlDelightCRUD<Payload>,
        block: suspend () -> Model
    ): Flow<List<Payload>> {
        return flow<List<Payload>> {
            val cachedItems = getAndMaybeEmitItemsFromCache(sqlDelightCRUD)
            val response = block()
            if (response != cachedItems) {
                cacheAndEmitItems(response, sqlDelightCRUD)
            }
        }
    }

    private suspend fun <T> FlowCollector<List<T>>.getAndMaybeEmitItemsFromCache(sqlDelightCRUD: SqlDelightCRUD<T>): List<T> {
        val cachedItems = sqlDelightCRUD.getItems()
        if (cachedItems.isNotEmpty()) {
            emit(cachedItems)
        }
        return cachedItems
    }

    private suspend fun <T> FlowCollector<List<T>>.cacheAndEmitItems(items: List<T>, sqlDelightCRUD: SqlDelightCRUD<T>) {
        sqlDelightCRUD.replaceAll(items)
        emit(items)
    }
}
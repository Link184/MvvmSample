package com.link184.products.core.model

import com.link184.products.core.sql.Entity


interface EntityCollection<E : Entity> {
    fun getEntities(): List<E>
}
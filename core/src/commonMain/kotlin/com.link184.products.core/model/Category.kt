package com.link184.products.core.model

import com.link184.products.core.sql.Entity
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Category(
    val id: String,
    val name: String,
    val description: String,
    val products : List<Product>,
    @Transient
    override val isFromCache: Boolean = false
): Entity
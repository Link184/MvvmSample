package com.link184.products.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: String,
    val name: String,
    val description: String,
    val products : List<Product>
)
package com.link184.products.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: String,
    val categoryId: String,
    val name: String,
    val url: String,
    val description: String,
    val salePrice: SalePrice
)
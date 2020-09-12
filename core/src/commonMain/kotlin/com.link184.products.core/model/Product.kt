package com.link184.products.core.model

import kotlinx.serialization.Serializable

//"id": "1",
//"categoryId": "36802",
//"name": "Bread",
//"url": "/Bread.jpg",
//"description": "",
//"salePrice": {
//    "amount": "0.81",
//    "currency": "EUR"
//}
@Serializable
data class Product(
    val id: String,
    val categoryId: String,
    val name: String,
    private val url: String,
    val description: String,
    val salePrice: SalePrice
)
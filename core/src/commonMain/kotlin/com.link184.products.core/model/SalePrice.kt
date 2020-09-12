package com.link184.products.core.model

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import com.link184.products.core.serialization.BigDecimalSerializer
import kotlinx.serialization.Serializable

@Serializable
data class SalePrice(
    @Serializable(with = BigDecimalSerializer::class) val amount: BigDecimal,
    val currency: Currency
)
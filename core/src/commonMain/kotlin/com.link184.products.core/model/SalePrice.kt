package com.link184.products.core.model

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import com.link184.products.core.serialization.BigDecimalSerializer
import kotlinx.serialization.Serializable

@Serializable
data class SalePrice(
        @Serializable(with = BigDecimalSerializer::class) val amount: BigDecimal,
        val currency: Currency
) : com.link184.products.core.Serializable {
    fun toExplicitString() = "${amount.relevantString()} $currency"

    private fun BigDecimal.relevantString(): String {
        var stringExpanded = toStringExpanded()
        if (stringExpanded.last() == '.') {
            return stringExpanded.dropLast(1)
        }
        return stringExpanded
    }
}
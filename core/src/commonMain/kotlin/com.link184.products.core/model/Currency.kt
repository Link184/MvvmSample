package com.link184.products.core.model

import kotlinx.serialization.Serializable

@Serializable
enum class Currency {
    EUR,
    USD;

    companion object {
        fun resolveCurrency(currencyName: String): Currency {
            return values()
                    .find { it.name.equals(currencyName, true) }
                    ?: throw IllegalStateException("Unsupported currency")
        }
    }
}
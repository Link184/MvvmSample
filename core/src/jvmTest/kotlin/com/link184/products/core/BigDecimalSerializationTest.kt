package com.link184.products.core

import com.link184.products.core.model.SalePrice
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.Test
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class BigDecimalSerializationTest {
    private val price = "9232.2123121432543634234234234234"
    private val salePriceJsonString =  "{" +
            "\"amount\":\"$price\"," +
            "\"currency\":\"EUR\"" +
            "}"
    private val salePrice = Json.decodeFromString<SalePrice>(salePriceJsonString)

    @Test
    fun `deserialization|serialization test`() {
        assertEquals(price, salePrice.amount.toStringExpanded())

        val newSalePriceJsonString = Json.encodeToString<SalePrice>(salePrice)
        assertEquals(salePriceJsonString, newSalePriceJsonString)
    }
}
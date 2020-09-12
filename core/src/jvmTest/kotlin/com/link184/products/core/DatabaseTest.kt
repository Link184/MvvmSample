package com.link184.products.core

import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import com.link184.products.core.model.Category
import com.link184.products.core.model.Currency
import com.link184.products.core.model.Product
import com.link184.products.core.model.SalePrice
import com.link184.products.core.sql.CategoryDAO
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(JUnit4::class)
class DatabaseTest {
    @Test
    fun `categories test`() {
        val categoryDAO = CategoryDAO(TestPersistentSession())
        val categoryResponse = Json.decodeFromString<List<Category>>(categories)
        categoryDAO.insertAll(categoryResponse)

        var categories = categoryDAO.getItems()
        assertEquals(categoryResponse, categories)

        val testItem = Category(
            "666",
            "TestName",
            "TestDescription",
            listOf(
                Product("444", "666", "TestName", "TestUrl", "TestDesc", SalePrice("5.55".toBigDecimal(), Currency.EUR)),
                Product("555", "666", "TestName2", "TestUrl2", "TestDesc2", SalePrice("445.5555".toBigDecimal(), Currency.EUR))
            )
        )
        categoryDAO.insertItem(testItem)
        categories = categoryDAO.getItems()
        assertTrue { categories.contains(testItem) }
    }
}
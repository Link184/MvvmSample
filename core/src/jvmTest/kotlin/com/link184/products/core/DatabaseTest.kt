package com.link184.products.core

import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import com.link184.products.core.model.Category
import com.link184.products.core.model.Currency
import com.link184.products.core.model.Product
import com.link184.products.core.model.SalePrice
import com.link184.products.core.sql.CategoryDAO
import com.link184.products.core.sql.ProductDAO
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
        val sqlPersistence = TestPersistentSession()
        val categoryDAO = CategoryDAO(sqlPersistence)
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

        testProducts(sqlPersistence, testItem)
    }

    private fun testProducts(persistentSession: PersistentSession, testCategory: Category) {
        val productDAO = ProductDAO(persistentSession)

        val persistentProducts: List<Product> = productDAO.getItems()

        assertTrue {
            testCategory.products.map { prodcut ->
                persistentProducts.find {
                    it.id == prodcut.id
                            && it.url == prodcut.url
                            && it.categoryId == prodcut.categoryId
                            && it.description == prodcut.description
                } != null
            }.reduce { acc, b ->
                acc && b
            }
        }

        val selectedProduct = productDAO.selectByProductIdAndCategoryId(testCategory.products.first().id, testCategory.id)
        assertTrue {
            testCategory.products.first().let {
                it.id == selectedProduct.id
                it.categoryId == testCategory.id
            }
        }
    }
}
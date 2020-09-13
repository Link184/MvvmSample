package com.link184.products.core.repository

import com.link184.products.core.PersistentSession
import com.link184.products.core.model.Category
import com.link184.products.core.model.Product
import com.link184.products.core.service.CategoriesService
import com.link184.products.core.sql.CategoryDAO
import com.link184.products.core.sql.ProductDAO
import kotlinx.coroutines.flow.Flow

class CategoriesRepository(
        service: CategoriesService,
        private val persistentSession: PersistentSession
) : BaseRepository<CategoriesService>(service) {
    private val categoryDAO = CategoryDAO(persistentSession)
    private val productDAO by lazy { ProductDAO(persistentSession) }

    fun getCategories(): Flow<List<Category>> = exchangeCachedFlow(categoryDAO) {
        service.getCategories().injectUrlSuffixesToAllProducts()
    }

    private fun List<Category>.injectUrlSuffixesToAllProducts(): List<Category> {
        return map { category ->
            val productsWithInjectedUrls = category.products.map {
                it.copy(url = persistentSession.mediaHostingBaseUrl + it.url)
            }
            category.copy(products = productsWithInjectedUrls)
        }
    }

    fun getPersistentProductByCategoryIdAndProductId(categoryId: String, productId: String): Product {
        return productDAO.selectByProductIdAndCategoryId(productId, categoryId)
    }
}
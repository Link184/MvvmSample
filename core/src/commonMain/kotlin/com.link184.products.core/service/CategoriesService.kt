package com.link184.products.core.service

import com.link184.products.core.model.Category


interface CategoriesService {
    suspend fun getCategories(): List<Category>
}
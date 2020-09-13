package com.link184.products.core.service

import com.link184.products.core.Session
import com.link184.products.core.model.Category

class AwsCategoriesService(session: Session) : HttpService(session), CategoriesService {
    override suspend fun getCategories(): List<Category> {
        return get("")
    }
}
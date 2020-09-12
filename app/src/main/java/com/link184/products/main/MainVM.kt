package com.link184.products.main

import com.link184.architecture.mvvm.common.CollectionLiveData
import com.link184.products.base.BaseVM
import com.link184.products.core.model.Category
import com.link184.products.core.repository.CategoriesRepository

class MainVM(private val repository: CategoriesRepository): BaseVM() {
    val categories = CollectionLiveData<Category>()

    override fun attachView() {
        repository.getCategories().launchFlow(
            onNext = {
                categories.resetList(it)
            }
        )
    }

    override fun onRefresh() {
        attachView()
    }
}
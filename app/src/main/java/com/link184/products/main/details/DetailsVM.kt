package com.link184.products.main.details

import androidx.lifecycle.MutableLiveData
import com.link184.architecture.mvvm.base.DataState
import com.link184.architecture.mvvm.common.Event
import com.link184.products.base.BaseVM
import com.link184.products.core.model.Product
import com.link184.products.core.repository.CategoriesRepository

class DetailsVM(
    private val repository: CategoriesRepository
) : BaseVM() {
    val product = MutableLiveData<Event<Product>>()

    fun resolveProduct(categoryId: String, productId: String) {
        kotlin.runCatching {
            repository.getPersistentProductByCategoryIdAndProductId(categoryId, productId)
        }.onSuccess {
            product.postValue(Event(it))
        }.onFailure {
            state.postValue(DataState.Fail<Product>(it))
        }
    }
}

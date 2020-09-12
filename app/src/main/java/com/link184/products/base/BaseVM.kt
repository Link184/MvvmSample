package com.link184.products.base

import androidx.lifecycle.viewModelScope
import com.link184.architecture.mvvm.base.BaseViewModel
import com.link184.architecture.mvvm.base.DataState
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class BaseVM : BaseViewModel() {
    @OptIn(InternalCoroutinesApi::class)
    fun <T> Flow<T>.launchFlow(onNext: (T) -> Unit, onError: (Throwable) -> Unit = {}) {
        state.postValue(DataState.Progress)
        viewModelScope.launch {
            try {
                collect { value ->
                    state.postValue(DataState.Success(value))
                    onNext(value)
                }
            } catch (e: Throwable) {
                state.postValue(DataState.Fail<T>(e))
                onError(e)
            }
        }
    }
}
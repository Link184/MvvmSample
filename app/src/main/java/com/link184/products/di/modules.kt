package com.link184.products.di

import com.link184.products.AndroidPersistentSession
import com.link184.products.BuildConfig
import com.link184.products.core.PersistentSession
import com.link184.products.core.repository.CategoriesRepository
import com.link184.products.core.service.AwsCategoriesService
import com.link184.products.core.service.CategoriesService
import com.link184.products.core.service.FooCategoriesService
import com.link184.products.main.MainVM
import com.link184.products.main.details.DetailsVM
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModelsModule = module {
    single(named<PersistentSession>()) { AndroidPersistentSession(androidContext()) }
    single<CategoriesService>(named<AwsCategoriesService>()) { AwsCategoriesService(get(named<PersistentSession>())) }
    single<CategoriesService>(named<FooCategoriesService>()) {
        FooCategoriesService(get(
                named<PersistentSession>()
        ))
    }
    single {
        if (BuildConfig.DEBUG) {
            CategoriesRepository(get(named<AwsCategoriesService>()), get(named<PersistentSession>()))
        } else {
            CategoriesRepository(get(named<FooCategoriesService>()), get(named<PersistentSession>()))
        }
    }
}

val viewModule = module {
    viewModel { MainVM(get()) }
    viewModel { DetailsVM(get()) }
}
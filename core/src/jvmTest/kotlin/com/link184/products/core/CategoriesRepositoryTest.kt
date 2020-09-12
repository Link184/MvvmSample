package com.link184.products.core

import com.link184.products.core.model.Category
import com.link184.products.core.repository.CategoriesRepository
import com.link184.products.core.service.AwsCategoriesService
import com.link184.products.core.sql.CategoryDAO
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertTrue

@RunWith(JUnit4::class)
class CategoriesRepositoryTest {
    private val repository =
        CategoriesRepository(AwsCategoriesService(TestSession), TestPersistentSession())

    @InternalCoroutinesApi
    @Test
    fun `test empty cached items`() {
        val categoryDAOSpy = spy(CategoryDAO(TestPersistentSession()))
        categoryDAOSpy.clear()
        reset(categoryDAOSpy)

        repository.setPrivateMember("categoryDAO", categoryDAOSpy)

        val cachedCategories = categoryDAOSpy.getItems()
        assertTrue { cachedCategories.isNullOrEmpty() }

        runBlocking {
            repository.getCategories()
                .onCompletion {
                    verify(categoryDAOSpy, times(1)).insertAll(any())
                }
                .collect { }

            reset(categoryDAOSpy)

            repository.getCategories()
                .onCompletion {
                    verify(categoryDAOSpy, times(0)).insertAll(any())
                }
                .collect { }
        }
    }

    @Test
    fun `test out-of-date cached items`() {
        val categoryDAOSpy = spy(CategoryDAO(TestPersistentSession()))
        categoryDAOSpy.clear()
        reset(categoryDAOSpy)

        repository.setPrivateMember("categoryDAO", categoryDAOSpy)

        val cachedCategories = categoryDAOSpy.getItems()
        assertTrue { cachedCategories.isNullOrEmpty() }

        val testCategory = Category("444", "TestCategory", "TestDescription", emptyList())
        runBlocking {
            repository.getCategories()
                .onCompletion {
                    verify(categoryDAOSpy, times(2)).replaceAll(any())
                }
                .collect {
                    val newCategoriesList = it.toMutableList().apply { add(testCategory) }
                    categoryDAOSpy.replaceAll(newCategoriesList)
                }

            reset(categoryDAOSpy)

            repository.getCategories()
                .onCompletion {
                    verify(categoryDAOSpy, times(1)).replaceAll(any())
                }
                .collect {
                }
        }
    }

    @Test
    fun `test injected image urls`() {
        runBlocking {
            repository.getCategories().safeCollect(
                onSuccess = { categories ->
                    categories.all {category ->
                        category.products.all {
                            it.url.startsWith(TestSession.mediaHostingBaseUrl)
                        }
                    }
                },
                onError = {
                    throw it
                }
            )
        }
    }
}
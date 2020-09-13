package com.link184.products.core

import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import java.lang.reflect.Modifier
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible


@OptIn(InternalCoroutinesApi::class)
suspend fun <T> Flow<T>.safeCollect(onSuccess: suspend (T) -> Unit, onError: (Throwable) -> Unit = { }): Flow<T> {
    try {
        collect(onSuccess)
    } catch (e: Exception) {
        onError(e)
    }
    return this
}

inline fun <reified T : Any> T.getPrivateMember(memberName: String) =
        T::class.declaredMemberProperties
                .firstOrNull { it.name == memberName }
                ?.let {
                    it.isAccessible = true
                    it.get(this@getPrivateMember)
                }

fun Any.setPrivateMember(fieldName: String, newValue: Any?) =
        this::class.java.getDeclaredField(fieldName)
                .apply {
                    isAccessible = true
                    val modifiersField = this::class.java.getDeclaredField("modifiers")
                    modifiersField.isAccessible = true
                    modifiersField.setInt(this, this.modifiers and Modifier.FINAL.inv())
                    set(this@setPrivateMember, newValue)
                }
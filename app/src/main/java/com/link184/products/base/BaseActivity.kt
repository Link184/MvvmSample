package com.link184.products.base

import com.link184.architecture.mvvm.base.MvvmActivity
import com.link184.extensions.alert
import com.link184.extensions.loge
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.parameter.emptyParametersHolder
import org.koin.core.qualifier.Qualifier
import kotlin.reflect.KClass

abstract class BaseActivity<VM : BaseVM>(
        clazz: KClass<VM>,
        qualifier: Qualifier? = null,
        parameters: ParametersDefinition = { emptyParametersHolder() }
) : MvvmActivity<VM>(clazz, qualifier, parameters) {
    override fun onError(t: Throwable) {
        super.onError(t)
        loge("Activity error handler: ", t)
        alert("Activity error handler: ", t.localizedMessage)
    }
}
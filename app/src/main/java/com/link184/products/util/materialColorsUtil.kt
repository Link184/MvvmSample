package com.link184.products.util

import android.content.Context
import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.link184.products.BuildConfig
import kotlin.random.Random

val materialColorResources =
    Class.forName("${BuildConfig.APPLICATION_ID}.R\$color").declaredFields.let { fields ->
        fields.filter {
            it.name.startsWith("material_")
        }
            .map {
                it.getInt(null)
            }
    }

@ColorRes
fun randomMaterialColorRes() = materialColorResources.random(Random(System.nanoTime()))

@ColorInt
fun Context.randomMaterialColor() = ContextCompat.getColor(this, randomMaterialColorRes())

fun Int.removeAlpha(): Int {
    return Color.rgb(Color.red(this), Color.green(this), Color.blue(this))
}

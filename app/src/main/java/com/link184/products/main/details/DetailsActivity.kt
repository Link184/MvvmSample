package com.link184.products.main.details

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.util.Pair
import androidx.core.view.ViewCompat
import androidx.palette.graphics.Palette
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.link184.extensions.loadUrl
import com.link184.products.R
import com.link184.products.base.BaseActivity
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : BaseActivity<DetailsVM>(DetailsVM::class) {
    override val layoutId: Int? = R.layout.activity_details
    override val render: DetailsVM.() -> Unit = {
        val productId = intent?.getStringExtra(PRODUCT_ID_EXTRA_KEY)
        val categoryId = intent?.getStringExtra(CATEGORY_ID_EXTRA_KEY)

        if (productId != null && categoryId != null) {
            resolveProduct(categoryId, productId)
        }

        product observeEvent { product ->
            productName.text = product.name
            productPrice.text = product.salePrice.toExplicitString()
            productImage.loadUrl(product.url) {
                listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        productImage.setImageResource(R.drawable.ic_baseline_error)
                        return true
                    }

                    override fun onResourceReady(resource: Drawable, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        Palette.from(resource.toBitmap()).generate { palette ->
                            palette?.vibrantSwatch?.titleTextColor?.let { textColor ->
                                rootContainer.setBackgroundColor(palette.vibrantSwatch!!.rgb)
                                productName.setTextColor(textColor)
                                productPrice.setTextColor(textColor)
                            }
                        }
                        return false
                    }
                })
            }
        }
    }

    companion object {
        private const val CATEGORY_ID_EXTRA_KEY = "categoryID"
        private const val PRODUCT_ID_EXTRA_KEY = "productID"
        fun startActivity(context: Activity, categoryId: String, productId: String, vararg sharedViews: View) {
            val transitionViewPairs = sharedViews.map {
                Pair.create(it, ViewCompat.getTransitionName(it))
            }.toTypedArray()

            context.startActivity(
                Intent(context, DetailsActivity::class.java)
                    .putExtra(CATEGORY_ID_EXTRA_KEY, categoryId)
                    .putExtra(PRODUCT_ID_EXTRA_KEY, productId),
                ActivityOptionsCompat.makeSceneTransitionAnimation(context, *transitionViewPairs).toBundle()
            )
        }
    }
}
package com.link184.products.main

import androidx.palette.graphics.Palette
import com.link184.extensions.loadUrl
import com.link184.extensions.onClick
import com.link184.kidadapter.setUp
import com.link184.products.R
import com.link184.products.base.BaseActivity
import com.link184.products.core.model.Category
import com.link184.products.core.model.Product
import com.link184.products.main.details.DetailsActivity
import com.link184.products.util.randomMaterialColor
import com.link184.products.util.removeAlpha
import kotlinx.android.synthetic.main.item_cateogry.view.*
import kotlinx.android.synthetic.main.item_product.view.*

class MainActivity : BaseActivity<MainVM>(MainVM::class) {
    override val layoutId: Int = R.layout.activity_main
    override val render: MainVM.() -> Unit = {
        val adapter = powerView?.recyclerView?.setUp {}

        categories observe { categories ->
            adapter?.restructure {
                removeAll()
                categories.forEach { category ->
                    insertBottom("Category ${category.id}") {
                        withLayoutResId(R.layout.item_cateogry)
                        withItem(category)
                        bind<Category> {
                            categoryName.text = it.name
                            categoryDescription.text = it.description
                        }
                    }

                    insertBottom("Products ${category.id}") {
                        withLayoutResId(R.layout.item_product)
                        withItems(category.products.toMutableList())
                        bind<Product> {
                            val backgroundColor = randomMaterialColor()
                            setBackgroundColor(backgroundColor)

                            val textColor = Palette.Swatch(backgroundColor.removeAlpha(), width * height)
                                .titleTextColor

                            productName.text = it.name
                            productName.setTextColor(textColor)
                            productDescription.text = it.description
                            productDescription.setTextColor(textColor
                            )
                            productThumbnail.loadUrl(it.url) {
                                error(R.drawable.ic_baseline_error)
                            }
                            onClick {
                                DetailsActivity.startActivity(this@MainActivity,
                                    it.categoryId, it.id,productName, productThumbnail)
                            }
                        }
                    }
                }
            }
        }
    }
}
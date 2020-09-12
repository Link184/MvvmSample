package com.link184.products.main.details

import com.link184.products.base.BaseActivity

class DetailsActivity: BaseActivity<DetailsVM>(DetailsVM::class) {
    override val layoutId: Int?
    override val render: DetailsVM.() -> Unit
}
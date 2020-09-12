package com.link184.products.main

import com.link184.products.R
import com.link184.products.base.BaseActivity

class MainActivity : BaseActivity<MainVM>(MainVM::class) {
    override val layoutId: Int = R.layout.activity_main
    override val render: MainVM.() -> Unit = {

    }
}
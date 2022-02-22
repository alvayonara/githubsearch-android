package com.alvayonara.githubsearch.ui.main

import android.view.LayoutInflater
import com.alvayonara.githubsearch.core.base.BaseActivity
import com.alvayonara.githubsearch.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setup() {
    }
}
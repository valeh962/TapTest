package com.tap.test.ui.activity.main

import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.tap.mod_base.BaseActivity
import com.tap.test.R
import com.tap.test.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    ActivityMainBinding::inflate,
    MainViewModel::class.java
) {

    private lateinit var navController: NavController

    override fun setupView() {
        navController = findNavController(R.id.fragment)
        navController.graph = navController.navInflater.inflate(R.navigation.navigation)

    }

    override fun observeData() {

    }
}
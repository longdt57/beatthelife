/*
 * Created by do thanh long at 1/22/21 10:16 PM.
 * Copyright (c) 2021. All rights reserved.
 */

package com.lee.group.beatthelife

import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lee.group.beatthelife.databinding.ActivityMainBinding
import com.lee.group.beatthelife.ui.utils.redirectToOnBoardingScreen
import com.lee.group.beatthelife.ui.utils.setupTrackerUserId
import dagger.hilt.android.AndroidEntryPoint
import lee.group.auth.base.BaseAuthenticatedActivity

@AndroidEntryPoint
class MainActivity : BaseAuthenticatedActivity<ActivityMainBinding, MainViewModel>() {

    override fun provideBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override val viewModel: MainViewModel by viewModels()

    override fun setupUI() {
        viewModel.logDeviceType()
        setupNavigation()
        setupTrackerUserId()
    }

    private fun setupNavigation() {
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSignedOut() {
        redirectToOnBoardingScreen()
    }

    override fun onSignedIn() = Unit
}

package com.lee.group.beatthelife

import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.lee.group.beatthelife.data.UserManager
import com.lee.group.beatthelife.databinding.ActivityMainBinding
import com.lee.group.beatthelife.ui.base.BaseAuthenticatedActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseAuthenticatedActivity<ActivityMainBinding, MainViewModel>() {

    override fun provideBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override val viewModel: MainViewModel by viewModels()

    override fun setupUI() {
        setupNavigation()
        setupFirebaseCrashlytic()
        setupFirebaseAnalytic()
    }

    override fun setupViewModel() = Unit

    private fun setupFirebaseAnalytic() {
        val userId = UserManager.getCurrentUserId().orEmpty()
        FirebaseAnalytics.getInstance(this).setUserId(userId)
    }

    private fun setupFirebaseCrashlytic() {
        val userId = UserManager.getCurrentUserId().orEmpty()
        FirebaseCrashlytics.getInstance().setUserId(userId)
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
}

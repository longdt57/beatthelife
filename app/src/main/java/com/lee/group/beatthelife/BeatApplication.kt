package com.lee.group.beatthelife

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BeatApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initWorker()
    }

    @Inject
    lateinit var hiltWorkerFactory: HiltWorkerFactory

    private fun initWorker() {
        val configuration = Configuration.Builder()
            .setWorkerFactory(hiltWorkerFactory)
            .build()
        WorkManager.initialize(this, configuration)
    }
}

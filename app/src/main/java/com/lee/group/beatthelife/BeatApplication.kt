/*
 * Created by do thanh long at 1/22/21 10:16 PM.
 * Copyright (c) 2021. All rights reserved.
 */

package com.lee.group.beatthelife

import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import com.lee.group.beatthelife.tracing.AppTracingName.TRACING_INIT_TRACKER_SDK
import com.lee.group.beatthelife.tracing.AppTracingName.TRACING_INIT_WORKER
import com.lee.group.beatthelife.tracing.PerformanceTracing.oneMeasureDuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import lee.group.core.base.startup.BaseApplication
import lee.group.tracking.TrackerSDK
import lee.group.tracking.tracker.FirebaseAnalysisTracker

@HiltAndroidApp
class BeatApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        executeInBackground {
            oneMeasureDuration(TRACING_INIT_WORKER) {
                initWorker()
            }
        }

        executeInBackground {
            oneMeasureDuration(TRACING_INIT_TRACKER_SDK) {
                initTracker()
            }
        }
    }

    @Inject
    lateinit var hiltWorkerFactory: HiltWorkerFactory

    private fun initWorker() {
        val configuration = Configuration.Builder()
            .setWorkerFactory(hiltWorkerFactory)
            .build()
        WorkManager.initialize(this, configuration)
    }

    @Inject
    lateinit var firebaseTracker: FirebaseAnalysisTracker

    private fun initTracker() {
        TrackerSDK.addTracker(firebaseTracker)
    }
}

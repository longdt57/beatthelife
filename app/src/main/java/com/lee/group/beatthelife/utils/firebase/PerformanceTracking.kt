package com.lee.group.beatthelife.utils.firebase

import com.google.firebase.perf.FirebasePerformance

object PerformanceTracking {

    fun oneMeasureDuration(message: String = "", code: () -> Unit) {
        val traceName = message.takeIf { it.isNotBlank() } ?: code.toString()

        val myTrace = FirebasePerformance.getInstance().newTrace(traceName)
        myTrace.start()
        code.invoke()
        myTrace.stop()
    }
}

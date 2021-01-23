/*
 * Created by do thanh long on 1/23/21 9:56 AM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/23/21 9:56 AM
 */

package com.lee.group.beatthelife.tracing

import com.google.firebase.perf.FirebasePerformance

object PerformanceTracing {

    fun oneMeasureDuration(message: String = "", code: () -> Unit) {
        val traceName = message.takeIf { it.isNotBlank() } ?: code.toString()

        val myTrace = FirebasePerformance.getInstance().newTrace(traceName)
        myTrace.start()
        code.invoke()
        myTrace.stop()
    }
}

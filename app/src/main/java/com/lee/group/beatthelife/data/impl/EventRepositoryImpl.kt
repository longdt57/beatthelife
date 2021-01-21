package com.lee.group.beatthelife.data.impl

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.lee.group.beatthelife.data.IEventRepository
import com.lee.group.beatthelife.data.utils.EventParam.DEVICE_NAME
import javax.inject.Inject
import lee.group.core.ext.deviceName

class EventRepositoryImpl @Inject constructor() : IEventRepository {

    private val firebaseAnalytics get() = Firebase.analytics

    override fun logEventLogin() {
        firebaseAnalytics.logEvent(
            FirebaseAnalytics.Event.LOGIN,
            Bundle().apply {
                putString(DEVICE_NAME, deviceName)
            }
        )
    }
}

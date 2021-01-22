package com.lee.group.beatthelife.data.impl

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.lee.group.beatthelife.data.IEventRepository
import com.lee.group.beatthelife.data.utils.EventParam
import com.lee.group.beatthelife.data.utils.EventParam.DEVICE_NAME
import javax.inject.Inject
import lee.group.core.ext.DeviceUtil
import lee.group.tracking.TrackerSDK
import lee.group.tracking.model.LeeEvent

class EventRepositoryImpl @Inject constructor() : IEventRepository {

    override fun logEventLogin() {
        val event = LeeEvent(
            FirebaseAnalytics.Event.LOGIN,
            Bundle().apply {
                putString(DEVICE_NAME, DeviceUtil.deviceName)
            }
        )
        TrackerSDK.logEvent(event)
    }

    override fun trackDeviceType() {
        val deviceStatus: String = when {
            DeviceUtil.isEmulator -> EventParam.DEVICE_EMULATOR
            else -> EventParam.DEVICE_NONE
        }
        TrackerSDK.setUserProperty(EventParam.DEVICE_STATUS, deviceStatus)
    }
}

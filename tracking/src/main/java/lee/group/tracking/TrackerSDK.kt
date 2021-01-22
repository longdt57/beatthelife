package lee.group.tracking

import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import java.lang.IllegalStateException
import lee.group.tracking.model.LeeEvent
import lee.group.tracking.tracker.BaseTracker

object TrackerSDK {

    private val trackers: MutableSet<BaseTracker<LeeEvent>> = mutableSetOf()

    fun addTracker(tracker: BaseTracker<LeeEvent>) {
        val isExist = trackers.firstOrNull { it.trackerType() == tracker.trackerType() } != null
        if (isExist) {
            throw IllegalStateException("$tracker is added")
        }
        trackers.add(tracker)
    }

    fun setUserId(userId: String) {
        trackers.forEach { tracker ->
            tracker.setUserId(userId)
        }
    }

    fun <E : LeeEvent> logEvent(event: E) {
        trackers.forEach { tracker ->
            tracker.logEvent(event)
        }
    }

    fun setUserProperty(key: String, value: String) {
        Firebase.analytics.setUserProperty(key, value)
    }
}

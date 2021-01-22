package lee.group.tracking.tracker

import lee.group.tracking.model.LeeEvent

abstract class BaseTracker<E : LeeEvent> {

    fun logEvent(event: E) {
        if (isValidToTrack(event).not()) return
        executeLogEvent(event)
    }

    abstract fun setUserId(userId: String?)

    abstract fun trackerType(): String

    abstract fun executeLogEvent(event: E)

    private fun isValidToTrack(event: E): Boolean {
        return event.trackerTypes.contains(trackerType())
    }
}

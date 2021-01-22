package lee.group.tracking.tracker

import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject
import lee.group.tracking.model.LeeEvent
import lee.group.tracking.tracker.utils.StandardTrackerType.FIREBASE

class FirebaseAnalysisTracker @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) : BaseTracker<LeeEvent>() {

    override fun setUserId(userId: String?) {
        firebaseAnalytics.setUserId(userId)
    }

    override fun trackerType(): String {
        return FIREBASE
    }

    override fun executeLogEvent(event: LeeEvent) {
        firebaseAnalytics.logEvent(
            event.name,
            event.params
        )
    }
}

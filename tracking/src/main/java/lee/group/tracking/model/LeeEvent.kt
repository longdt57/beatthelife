package lee.group.tracking.model

import android.os.Bundle
import lee.group.tracking.tracker.utils.StandardTrackerType.APP_FLY
import lee.group.tracking.tracker.utils.StandardTrackerType.FACEBOOK
import lee.group.tracking.tracker.utils.StandardTrackerType.FIREBASE
import lee.group.tracking.utils.EventParams
import lee.group.tracking.utils.EventParams.Group.GROUP_AUTH

open class LeeEvent constructor(
    val name: String,
    internal val params: Bundle,
    internal val trackerTypes: MutableSet<String> = mutableSetOf(FIREBASE)
) {

    class Builder(
        private val name: String,
        private var params: Bundle = Bundle()
    ) {

        private var trackerTypes: MutableSet<String> = mutableSetOf(FIREBASE)

        fun setTrackerType(types: MutableSet<String>): Builder {
            trackerTypes = types
            return this
        }

        fun setParams(bundle: Bundle): Builder {
            params = bundle
            return this
        }

        fun addParam(key: String, value: String): Builder {
            params.putString(key, value)
            return this
        }

        fun addEventGroup(
            eventGroup: String,
            domainName: String = EventParams.Domain.DEFAULT
        ): Builder {
            params.putString(EventParams.Common.EVENT_GROUP, eventGroup)
            params.putString(EventParams.Common.DOMAIN, domainName)
            return this
        }

        fun addEventGroupAuth(): Builder {
            addEventGroup(GROUP_AUTH)
            return this
        }

        fun addCustomTracker(type: String): Builder {
            trackerTypes.add(type)
            return this
        }

        fun addFirebaseTracker(): Builder {
            return addCustomTracker(FIREBASE)
        }

        fun addFacebookTracker(): Builder {
            return addCustomTracker(FACEBOOK)
        }

        fun addAppFly(): Builder {
            return addCustomTracker(APP_FLY)
        }

        fun addAllStandardTracker() {
            addFirebaseTracker()
            addFacebookTracker()
            addAppFly()
        }

        fun build(): LeeEvent {
            return LeeEvent(
                name = name,
                params = params,
                trackerTypes = trackerTypes
            )
        }
    }
}

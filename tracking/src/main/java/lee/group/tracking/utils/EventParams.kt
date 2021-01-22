package lee.group.tracking.utils

object EventParams {

    object Common {

        const val SCREEN_NAME = "screen_name"
        const val PREVIOUS_SCREEN = "previous_screen"
        const val EVENT_TIMESTAMP = "event_timestamp"
        const val APP_VERSION = "app_version"
        const val ERROR_MESSAGE = "error_message"
        const val EVENT_GROUP = "event_group"
        const val DOMAIN = "domain"
    }

    object Domain {
        const val DEFAULT = "general"
    }

    object Group {
        const val DEFAULT = "general"
        const val GROUP_AUTH= "group_auth"
    }
}

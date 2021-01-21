package one.module.chat.sdk.config

import one.module.chat.sdk.data.firestore.utils.ChatFirebaseConfig

class OneChatSDKConfig private constructor(
    internal var chatFirebaseConfig: ChatFirebaseConfig? = null
) {

    class Builder {
        private var firebaseProjectId: String? = null
        private var firebaseApiKey: String? = null
        private var firebaseApplicationId: String? = null

        fun setFireBaseProjectId(firebaseProjectId: String): Builder {
            this.firebaseProjectId = firebaseProjectId
            return this
        }

        fun setFirebaseApiKey(firebaseApiKey: String): Builder {
            this.firebaseApiKey = firebaseApiKey
            return this
        }

        fun setFirebaseApplicationId(firebaseApplicationId: String): Builder {
            this.firebaseApplicationId = firebaseApplicationId
            return this
        }

        fun build(): OneChatSDKConfig {
            return OneChatSDKConfig(
                ChatFirebaseConfig(
                    firebaseProjectId = firebaseProjectId,
                    firebaseApiKey = firebaseApiKey,
                    firebaseApplicationId = firebaseApplicationId
                )
            )
        }
    }
}

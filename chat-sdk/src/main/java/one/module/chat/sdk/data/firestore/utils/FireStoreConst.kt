package one.module.chat.sdk.data.firestore.utils

// FireStoreChatRef
internal const val FIRE_STORE_CHAT_COLLECTION_USER = "chat-user"
internal const val FIRE_STORE_CHAT_COLLECTION_GROUP = "chat-group"
internal const val FIRE_STORE_CHAT_COLLECTION_GROUP_SEEN_AT = "chat-group/{group_id}"

internal const val FIRE_STORE_CHAT_COLLECTION_MESSAGE_GROUP = "{group_id}"
internal const val FIRE_STORE_CHAT_COLLECTION_MESSAGE = "chat-message/{group_id}/message"

// FireStore Model
internal const val GROUP_PRIVATE_TYPE = "private_chat"
internal const val GROUP_PEOPLE_TYPE = "group_chat"

internal const val FIRE_STORE_USER_ID = "uid"

internal const val FIRE_STORE_GROUP_ID = "group_id"
internal const val FIRE_STORE_GROUP_LAST_MESSAGE = "last_message"
internal const val FIRE_STORE_GROUP_REMOVED = "removed"
internal const val FIRE_STORE_GROUP_MEMBERS = "members"

internal const val FIRE_STORE_MESSAGE_ID = "message_id"
internal const val FIRE_STORE_MESSAGE_CREATED_AT = "created_at"
internal const val FIRE_STORE_MESSAGE_REMOVED = "removed"
internal const val FIRE_STORE_MESSAGE_LISTEN_LIMIT = 2L

internal const val FIRE_STORE_MESSAGE_TEXT = "Text"

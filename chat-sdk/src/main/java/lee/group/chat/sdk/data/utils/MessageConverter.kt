package lee.group.chat.sdk.data.utils

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import java.util.UUID
import lee.group.chat.sdk.data.firestore.model.FireStoreMessage
import lee.group.chat.sdk.data.model.ChatMessage
import lee.group.chat.sdk.data.model.MessageStatus
import lee.group.chat.sdk.data.model.MessageType

internal fun QuerySnapshot.toChatMessages(): List<ChatMessage> {
    return documents.mapNotNull {
        try {
            it.toObject(FireStoreMessage::class.java)?.toChatMessage(it.getMessageStatus())
        } catch (e: Exception) {
            null
        }
    }
}

internal fun DocumentSnapshot.getMessageStatus(): String {
    return when (metadata.hasPendingWrites()) {
        false -> MessageStatus.SUCCESS
        else -> MessageStatus.SENDING
    }
}

internal fun List<FireStoreMessage>.toChatMessages(): List<ChatMessage> {
    return mapNotNull { item -> item.toChatMessage() }
}

internal fun FireStoreMessage.toChatMessage(
    @MessageStatus status: String = MessageStatus.SENDING
): ChatMessage? {
    if (removed) return null
    return ChatMessage(
        id = message_id,
        message = message,
        sender = created_by?.toChatUser(),
        type = getChatMessageType(),
        createdAt = created_at,
        updatedAt = updated_at,
        deepLink = deep_link,
        data = meta_data,
        subMessage = sub_message?.toSubMessage(),
        subMessageResponseType = sub_type,
        status = status
    )
}

internal fun FireStoreMessage.toSubMessage(): ChatMessage? {
    if (removed) return null
    return ChatMessage(
        id = message_id,
        message = message,
        sender = created_by?.toChatUser(),
        type = getChatMessageType(),
        createdAt = created_at,
        updatedAt = updated_at,
        deepLink = deep_link,
        data = meta_data
    )
}

// Get created by basic user
internal fun ChatMessage.toFireStoreMessage(): FireStoreMessage {
    return FireStoreMessage(
        message_id = id,
        message = message,
        created_by = sender?.toFireStoreMessageSender(),
        type = type,
        created_at = createdAt,
        updated_at = updatedAt,
        deep_link = deepLink,
        meta_data = data,
        sub_message = subMessage?.toFireStoreSubMessage(),
        sub_type = subMessageResponseType
    )
}

// Remove subMessage and subType
private fun ChatMessage.toFireStoreSubMessage(): FireStoreMessage {
    return FireStoreMessage(
        message_id = id,
        message = message,
        created_by = sender?.toFireStoreMessageSender(),
        type = type,
        created_at = createdAt,
        updated_at = updatedAt,
        deep_link = deepLink,
        meta_data = data
    )
}

fun ChatMessage.Companion.makeSendingMessage(
    message: String?,
    type: String = MessageType.TEXT,
    data: String? = null,
    subMessage: ChatMessage? = null,
    subMessageResponseType: String? = null
): ChatMessage = ChatMessage(
    id = UUID.randomUUID().toString(),
    message = message,
    type = type,
    data = data,
    status = MessageStatus.SENDING,
    sender = ChatUserManager.getCurrentActiveUser(),
    createdAt = System.currentTimeMillis(),
    subMessage = subMessage,
    subMessageResponseType = subMessageResponseType
)

fun ChatMessage.Companion.makeDeleteMessage(): ChatMessage = ChatMessage(
    id = UUID.randomUUID().toString(),
    message = "Message has been deleted",
    type = MessageType.TEXT,
    status = MessageStatus.SUCCESS,
    createdAt = System.currentTimeMillis()
)

// Todo add Url type
internal fun FireStoreMessage.getChatMessageType(): String {
    return when {
        custom_type.isNullOrBlank().not() -> custom_type.orEmpty()
        else -> type
    }
}

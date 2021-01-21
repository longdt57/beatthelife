package one.module.chat.sdk.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.annotation.StringDef
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import one.module.chat.sdk.data.model.MessageStatus.Companion.FAILED
import one.module.chat.sdk.data.model.MessageStatus.Companion.SENDING
import one.module.chat.sdk.data.model.MessageStatus.Companion.SUCCESS
import one.module.chat.sdk.data.model.MessageType.Companion.ADMIN_MESSAGE
import one.module.chat.sdk.data.model.MessageType.Companion.IMAGE
import one.module.chat.sdk.data.model.MessageType.Companion.NOTIFICATION
import one.module.chat.sdk.data.model.MessageType.Companion.NOTIFICATION_SURVEY
import one.module.chat.sdk.data.model.MessageType.Companion.SHARED
import one.module.chat.sdk.data.model.MessageType.Companion.TEXT
import one.module.chat.sdk.data.model.MessageType.Companion.URL_THUMB
import one.module.chat.sdk.data.utils.ChatUserManager
import org.json.JSONException
import org.json.JSONObject

@Keep
@Parcelize
data class ChatMessage(

    @SerializedName("id")
    val id: String = "",

    @SerializedName("message")
    val message: String? = "",

    @SerializedName("created_by")
    val sender: ChatUser? = null,

    @SerializedName("type")
    val type: String = TEXT,

    @SerializedName("created_at")
    val createdAt: Long = 0L,

    @SerializedName("updated_at")
    val updatedAt: Long = 0L,

    @SerializedName("status")
    @MessageStatus val status: String = SUCCESS,

    @SerializedName("deep_link")
    val deepLink: String? = null,

    @SerializedName("data")
    val data: String? = null,

    @SerializedName("sub_message")
    val subMessage: ChatMessage? = null,

    @SerializedName("sub_type")
    val subMessageResponseType: String? = null,

    @SerializedName("url_data")
    private val urlMetadata: String? = null,

    @SerializedName("quick_action")
    private var quickActionLabelMetadata: String? = null
) : Parcelable {

    val senderId: String
        get() = sender?.userId.orEmpty()

    val senderName: String
        get() = sender?.nickname.orEmpty()

    // Image url
    val url: String
        get() = message.orEmpty()

    val isMyMessage: Boolean
        get() = ChatUserManager.getCurrentActiveUID() == senderId

    val shortName: String?
        get() = senderName.split(" ").lastOrNull().orEmpty()

    val quickActionMetadata: String?
        get() = quickActionLabelMetadata.orEmpty()

    fun toSubMessage() = copy(subMessageResponseType = null, subMessage = null)

    fun getBindData(): String = when (type) {
        TEXT, IMAGE -> message.orEmpty()
        URL_THUMB -> urlMetadata.orEmpty()
        else -> data.orEmpty()
    }

    fun getAdminMessageActivity(): String = try {
        JSONObject(data).getString(KEY_DATA_ACTIVITY)
    } catch (e: JSONException) {
        ""
    }

    companion object {
        const val KEY_DATA_ACTIVITY = "activity"
    }
}

@StringDef(
    value = [
        TEXT,
        URL_THUMB,
        SHARED,
        IMAGE,
        NOTIFICATION,
        ADMIN_MESSAGE,
        NOTIFICATION_SURVEY
    ]
)
@Retention(AnnotationRetention.SOURCE)
annotation class MessageType {
    companion object {
        const val RED_LINE = "red_line"

        // Send/Receive TextMessageView
        const val TEXT = "text"

        // Send/Receive ImageMessageView
        const val IMAGE = "image"

        const val URL_THUMB = "url_thumb"

        // Receive...NotificationView
        const val NOTIFICATION = "fcm_notification"
        const val NOTIFICATION_BWT = "BWT"

        // Send/Receive CustomView
        const val SHARED = "shared_product"

        // Admin message
        const val ADMIN_MESSAGE = "admin_message"

        // receive survey notification
        const val NOTIFICATION_SURVEY = "survey"
    }
}

@StringDef(
    value = [SENDING, SUCCESS, FAILED]
)
@Retention(AnnotationRetention.SOURCE)
annotation class MessageStatus {
    companion object {
        const val SENDING = "sending"
        const val SUCCESS = "success"
        const val FAILED = "failed"
    }
}

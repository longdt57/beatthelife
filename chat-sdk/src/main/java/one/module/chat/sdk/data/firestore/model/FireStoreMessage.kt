package one.module.chat.sdk.data.firestore.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import one.module.chat.sdk.data.firestore.utils.FIRE_STORE_MESSAGE_CREATED_AT
import one.module.chat.sdk.data.firestore.utils.FIRE_STORE_MESSAGE_ID
import one.module.chat.sdk.data.firestore.utils.FIRE_STORE_MESSAGE_REMOVED
import one.module.chat.sdk.data.firestore.utils.FIRE_STORE_MESSAGE_TEXT

@Keep
@Parcelize
internal data class FireStoreMessage(
    @SerializedName(FIRE_STORE_MESSAGE_CREATED_AT)
    val created_at: Long = 0,

    @SerializedName("updated_at")
    val updated_at: Long = 0,

    @SerializedName("created_by")
    val created_by: FireStoreUser? = null,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("type")
    val type: String = FIRE_STORE_MESSAGE_TEXT,

    @SerializedName("custom_type")
    val custom_type: String? = null,

    @SerializedName(FIRE_STORE_MESSAGE_ID)
    val message_id: String = "",

    @SerializedName("deep_link")
    val deep_link: String? = null,

    @SerializedName(FIRE_STORE_MESSAGE_REMOVED)
    val removed: Boolean = false,

    @SerializedName("meta_data")
    val meta_data: String? = null,

    @SerializedName("sub_message")
    val sub_message: FireStoreMessage? = null,

    @SerializedName("sub_type")
    val sub_type: String? = null

) : Parcelable

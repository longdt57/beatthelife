package lee.group.chat.sdk.data.firestore.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import lee.group.chat.sdk.data.firestore.utils.FIRE_STORE_GROUP_ID
import lee.group.chat.sdk.data.firestore.utils.FIRE_STORE_GROUP_LAST_MESSAGE
import lee.group.chat.sdk.data.firestore.utils.FIRE_STORE_GROUP_REMOVED
import lee.group.chat.sdk.data.firestore.utils.GROUP_PRIVATE_TYPE

@Keep
@Parcelize
internal data class FireStoreGroup(
    @SerializedName(FIRE_STORE_GROUP_ID)
    val group_id: String = "",

    @SerializedName("cover_url")
    val cover_url: String = "",

    @SerializedName("created_at")
    val created_at: Long = 0,

    @SerializedName("created_by")
    val created_by: FireStoreUser? = null,

    @SerializedName("members")
    val members: List<FireStoreUser>? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName(FIRE_STORE_GROUP_LAST_MESSAGE)
    val last_message: FireStoreMessage? = null,

    @SerializedName("type")
    val type: String? = GROUP_PRIVATE_TYPE,

    @SerializedName("meta_data")
    val meta_data: String? = "",

    @SerializedName(FIRE_STORE_GROUP_REMOVED)
    val removed: Boolean = false

) : Parcelable {

    fun isValidGroup(): Boolean = removed.not()
}

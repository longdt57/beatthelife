package one.module.chat.sdk.data.firestore.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import one.module.chat.sdk.data.firestore.utils.FIRE_STORE_USER_ID

@Keep
@Parcelize
internal data class FireStoreUser(
    @SerializedName(FIRE_STORE_USER_ID)
    val uid: String = "",

    @SerializedName("display_name")
    val display_name: String? = null,

    @SerializedName("groups")
    val groups: List<String>? = null,

    @SerializedName("seen_at")
    val seen_at: Long? = null,

    @SerializedName("profile_url")
    val profile_url: String? = null
) : Parcelable

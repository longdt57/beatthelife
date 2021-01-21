package one.module.chat.sdk.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChatUser(
    val userId: String,
    val nickname: String? = null,
    val profileUrl: String? = null,
    val groups: List<String> = emptyList(),
    val seenAt: Long? = null
) : Parcelable {

    fun isOnline() = false
}

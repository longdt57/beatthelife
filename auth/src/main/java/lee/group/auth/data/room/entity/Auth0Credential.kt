/*
 * Created by do thanh long on 1/27/21 3:25 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/27/21 3:25 PM
 */

package lee.group.auth.data.room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import lee.group.auth.data.room.AuthRoomConst.ACCESS_TOKEN
import lee.group.auth.data.room.AuthRoomConst.EXPIRES_AT
import lee.group.auth.data.room.AuthRoomConst.ID_TOKEN
import lee.group.auth.data.room.AuthRoomConst.REFRESH_TOKEN
import lee.group.auth.data.room.AuthRoomConst.SCOPE
import lee.group.auth.data.room.AuthRoomConst.TOKEN_TYPE

@Parcelize
@Entity
data class Auth0Credential(

    @SerializedName(ACCESS_TOKEN)
    @ColumnInfo(name = ACCESS_TOKEN)
    val accessToken: String? = null,

    @SerializedName(TOKEN_TYPE)
    @ColumnInfo(name = TOKEN_TYPE)
    val type: String? = null,

    @SerializedName(ID_TOKEN)
    @PrimaryKey
    val idToken: String = "",

    @SerializedName(REFRESH_TOKEN)
    @ColumnInfo(name = REFRESH_TOKEN)
    val refreshToken: String? = null,

    @SerializedName(EXPIRES_AT)
    @ColumnInfo(name = EXPIRES_AT)
    val expiresAt: Long? = null,

    @SerializedName(SCOPE)
    @ColumnInfo(name = SCOPE)
    val scope: String? = null

) : Parcelable {

    constructor(credentials: com.auth0.android.result.Credentials) : this(
        accessToken = credentials.accessToken,
        type = credentials.type,
        idToken = credentials.idToken.orEmpty(),
        refreshToken = credentials.refreshToken,
        expiresAt = credentials.expiresAt?.time,
        scope = credentials.scope
    )
}

internal fun com.auth0.android.result.Credentials.toAuth0Credential() = Auth0Credential(this)

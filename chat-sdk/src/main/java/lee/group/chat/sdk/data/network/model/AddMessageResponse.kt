/*
 * Created by do thanh long on 1/31/21 11:13 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/31/21 11:13 PM
 */

package lee.group.chat.sdk.data.network.model

import com.google.gson.annotations.SerializedName

data class AddMessageResponse(

    @SerializedName("message_id")
    val messageId: String
)

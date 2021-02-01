/*
 * Created by do thanh long on 1/31/21 11:05 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/31/21 11:05 PM
 */

package lee.group.chat.sdk.data.network

import lee.group.chat.sdk.data.network.model.AddMessageResponse
import lee.group.core.network.ObjectItem
import retrofit2.http.GET
import retrofit2.http.Query

interface ChatService {

    @GET("addMessage")
    suspend fun addMessage(@Query("text") message: String): ObjectItem<AddMessageResponse>
}

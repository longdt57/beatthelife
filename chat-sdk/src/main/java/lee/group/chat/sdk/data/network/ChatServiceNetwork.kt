/*
 * Created by do thanh long on 1/31/21 10:22 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/31/21 10:22 PM
 */

package lee.group.chat.sdk.data.network

import android.content.Context
import javax.inject.Inject
import lee.group.chat.sdk.R
import lee.group.core.network.BaseService
import retrofit2.Retrofit

class ChatServiceNetwork @Inject constructor(
    private val context: Context
) : BaseService() {

    override fun createRetrofit(): Retrofit {
        return Builder()
            .addDefaultHeaders(context)
            .build(context.getString(R.string.chat_end_point))
    }
}

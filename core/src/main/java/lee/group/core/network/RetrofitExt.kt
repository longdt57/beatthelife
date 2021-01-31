/*
 * Created by do thanh long on 1/31/21 5:59 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/31/21 5:59 PM
 */

package lee.group.core.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

fun Retrofit.Builder.addDefaultCallAdapterFactory(): Retrofit.Builder {
    addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    return this
}

fun Retrofit.Builder.addDefaultConverterFactories(): Retrofit.Builder {
    val gson = GsonBuilder().create()
    addConverterFactory(GsonConverterFactory.create(gson))
    return this
}

fun OkHttpClient.Builder.addHeaders(headers: Map<String, String>): OkHttpClient.Builder {
    addInterceptor { chain ->
        val original = chain.request()
        val builder = original.newBuilder()

        for ((key, value) in headers) {
            builder.addHeader(key, value)
        }

        builder.method(original.method(), original.body())
        chain.proceed(builder.build())
    }
    return this
}

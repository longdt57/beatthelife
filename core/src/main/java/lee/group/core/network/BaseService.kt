/*
 * Created by do thanh long on 1/31/21 8:09 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/31/21 8:09 PM
 */

package lee.group.core.network

import android.content.Context
import lee.group.core.ext.getDeviceId
import lee.group.core.ext.getUserAgent
import lee.group.core.network.RetrofitConst.ACCESS_TOKEN
import lee.group.core.network.RetrofitConst.DEVICE_ID
import lee.group.core.network.RetrofitConst.USER_AGENT
import okhttp3.OkHttpClient
import retrofit2.Retrofit

abstract class BaseService {

    private val retrofit: Retrofit by lazy { createRetrofit() }

    fun <T> create(clazz: Class<T>): T {
        return retrofit.create(clazz)
    }

    abstract fun createRetrofit(): Retrofit

    open class Builder {

        private var clientBuilder: OkHttpClient.Builder? = null
        private var headers: MutableMap<String, String> = mutableMapOf()

        fun setClientBuilder(clientBuilder: OkHttpClient.Builder): Builder {
            this.clientBuilder = clientBuilder
            return this
        }

        fun addHeader(key: String, value: String): Builder {
            headers[key] = value
            return this
        }

        fun addAccessToken(accessToken: String): Builder {
            addHeader(ACCESS_TOKEN, accessToken)
            return this
        }

        fun addDefaultHeaders(context: Context): Builder {
            addHeader(USER_AGENT, context.getUserAgent())
            addHeader(DEVICE_ID, context.getDeviceId())
            return this
        }

        fun build(endpoint: String): Retrofit {
            val clientBuilder = clientBuilder ?: OkHttpClient.Builder()

            clientBuilder.addInterceptor { chain ->
                val original = chain.request()
                val builder = original.newBuilder()

                for ((key, value) in headers) {
                    builder.addHeader(key, value)
                }

                builder.method(original.method(), original.body())
                chain.proceed(builder.build())
            }

            val retrofitBuilder = Retrofit.Builder()
                .baseUrl(endpoint)
                .client(clientBuilder.build())
                .addDefaultCallAdapterFactory()
                .addDefaultConverterFactories()

            return retrofitBuilder.build()
        }
    }
}

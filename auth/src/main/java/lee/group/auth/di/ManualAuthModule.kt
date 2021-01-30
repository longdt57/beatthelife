/*
 * Created by do thanh long on 1/28/21 12:12 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/28/21 12:12 PM
 */

package lee.group.auth.di

import android.content.Context
import com.auth0.android.Auth0
import com.auth0.android.provider.WebAuthProvider
import lee.group.auth.R

internal object ManualAuthModule {

    fun provideLoginWebAuthBuilder(context: Context): WebAuthProvider.Builder {
        return WebAuthProvider.login(Auth0(context))
            .withScheme(context.getString(R.string.auth0_scheme))
            .withAudience(
                String.format(
                    context.getString(R.string.auth0_audience_url),
                    context.getString(R.string.com_auth0_domain)
                )
            )
    }

    fun provideLogoutWebAuthBuilder(context: Context): WebAuthProvider.LogoutBuilder {
        return WebAuthProvider.logout(Auth0(context))
            .withScheme(context.getString(R.string.auth0_scheme))
    }
}

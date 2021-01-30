/*
 * Created by do thanh long on 1/28/21 10:18 AM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/28/21 10:18 AM
 */

package lee.group.auth.data.auth0

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthSecurePreferences {

    companion object {
        const val PREF_NAME = "lee.group.auth_pref"
    }
}

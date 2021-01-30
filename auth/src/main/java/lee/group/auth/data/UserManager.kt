/*
 * Created by do thanh long on 1/28/21 2:59 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/27/21 6:08 PM
 */

package lee.group.auth.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object UserManager {

    fun isLogin(): Boolean {
        return FirebaseAuth.getInstance().currentUser != null
    }

    fun getCurrentUser(): FirebaseUser? = FirebaseAuth.getInstance().currentUser

    fun getCurrentUserId(): String? = FirebaseAuth.getInstance().currentUser?.uid
}

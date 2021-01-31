/*
 * Created by do thanh long on 1/30/21 11:36 AM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/28/21 3:00 PM
 */

package lee.group.auth.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object FirebaseUserManager {

    fun isLogin(): Boolean {
        return FirebaseAuth.getInstance().currentUser != null
    }

    fun getCurrentUser(): FirebaseUser? = FirebaseAuth.getInstance().currentUser

    fun getCurrentUserId(): String? = FirebaseAuth.getInstance().currentUser?.uid
}

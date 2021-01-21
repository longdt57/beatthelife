package com.lee.group.beatthelife.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object UserManager {

    fun isLogin() = FirebaseAuth.getInstance().currentUser != null

    fun getCurrentUser(): FirebaseUser? = FirebaseAuth.getInstance().currentUser

    fun getCurrentUserId(): String? = FirebaseAuth.getInstance().currentUser?.uid
}
package com.lee.group.beatthelife.data

import io.reactivex.Single

interface IBeoURepository {

    fun signIn()

    fun signOut(): Single<Boolean>
}

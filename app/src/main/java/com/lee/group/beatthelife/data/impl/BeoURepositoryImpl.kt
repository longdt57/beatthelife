package com.lee.group.beatthelife.data.impl

import android.content.Context
import com.firebase.ui.auth.AuthUI
import com.lee.group.beatthelife.data.IBeoURepository
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class BeoURepositoryImpl @Inject constructor(
    private val context: Context
) : IBeoURepository {

    @ExperimentalCoroutinesApi
    override suspend fun signOut(): Flow<Boolean> = callbackFlow {
        AuthUI.getInstance()
            .signOut(context)
            .addOnCompleteListener {
                when {
                    it.exception != null -> close(it.exception!!)
                    else -> {
                        offer(true)
                        close()
                    }
                }
            }
        awaitClose {}
    }
}

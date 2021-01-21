package com.lee.group.beatthelife.data.impl

import android.content.Context
import com.firebase.ui.auth.AuthUI
import com.lee.group.beatthelife.data.IBeoURepository
import io.reactivex.Single
import javax.inject.Inject

class BeoURepositoryImpl @Inject constructor(
    private val context: Context
) : IBeoURepository {

    override fun signIn() {
        TODO("Not yet implemented")
    }

    override fun signOut(): Single<Boolean> {
        return Single.create { emitter ->
            AuthUI.getInstance()
                .signOut(context)
                .addOnCompleteListener {
                    when {
                        emitter.isDisposed -> return@addOnCompleteListener
                        it.exception != null -> emitter.onError(it.exception!!)
                        else -> emitter.onSuccess(true)
                    }
                }
        }
    }
}

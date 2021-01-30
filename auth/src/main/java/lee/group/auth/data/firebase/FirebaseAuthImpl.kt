package lee.group.auth.data.firebase

import android.content.Context
import com.firebase.ui.auth.AuthUI
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

internal class FirebaseAuthImpl @Inject constructor(
    private val context: Context
) : IFirebaseAuth {

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

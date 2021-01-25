package lee.group.chat.sdk.data.firestore.utils

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope
import lee.group.chat.sdk.data.firestore.error.FireStoreDataNotExistException

@ExperimentalCoroutinesApi
internal inline fun <reified T> ProducerScope<T>.onProducerScopeOffer(
    exception: Exception?,
    data: T?
) {
    when {
        isClosedForSend -> return
        exception != null -> close(exception)
        data != null -> offer(data)
        else -> close(FireStoreDataNotExistException())
    }
}

@ExperimentalCoroutinesApi
internal inline fun <reified T> ProducerScope<List<T>>.onProducerScopeOffer(
    exception: Exception?,
    data: List<T>
) {
    when {
        isClosedForSend -> return
        exception != null -> close(exception)
        else -> offer(data)
    }
}

@ExperimentalCoroutinesApi
internal inline fun <reified T> ProducerScope<List<T>>.onFireStoreSnapshotListener(
    exception: Exception?,
    task: QuerySnapshot?
) {
    try {
        val data = task?.toObjects(T::class.java)
        onProducerScopeOffer(exception, data)
    } catch (ex: Exception) {
        close(ex)
    }
}

@ExperimentalCoroutinesApi
internal inline fun <reified T> ProducerScope<T>.onFireStoreSnapshotListener(
    exception: Exception?,
    task: DocumentSnapshot?
) {
    try {
        val data = task?.toObject(T::class.java)
        onProducerScopeOffer(exception, data)
    } catch (ex: Exception) {
        close(ex)
    }
}

/**
 * @throws Exception if map data fail.
 */
internal inline fun <reified T : Any> QuerySnapshot.toObjectsOrEmpty(): List<T> {
    return documents.mapNotNull {
        try {
            it.toObject(T::class.java)
        } catch (ex: Exception) {
            null
        }
    }
}

internal inline fun <reified T : Any> DocumentSnapshot.toObjectOrNull(): T? {
    return try {
        toObject(T::class.java)
    } catch (e: Exception) {
        null
    }
}

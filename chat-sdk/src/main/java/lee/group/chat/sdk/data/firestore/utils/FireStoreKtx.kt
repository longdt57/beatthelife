package lee.group.chat.sdk.data.firestore.utils

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.CompletableEmitter
import io.reactivex.ObservableEmitter
import io.reactivex.SingleEmitter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope
import lee.group.chat.sdk.data.firestore.error.FireStoreDataNotExistException

/*** RX Extension ***/
internal inline fun <reified T> SingleEmitter<T>.onRxSingleListener(
    exception: Exception?,
    data: T?
) {
    when {
        isDisposed -> return
        data != null -> onSuccess(data)
        exception != null -> onError(exception)
        else -> onError(FireStoreDataNotExistException())
    }
}

internal inline fun <reified T> ObservableEmitter<T>.onRxObservableListener(
    exception: Exception?,
    data: T?
) {
    when {
        isDisposed -> return
        data != null -> onNext(data)
        exception != null -> onError(exception)
        else -> onError(FireStoreDataNotExistException())
    }
}

@ExperimentalCoroutinesApi
internal inline fun <reified T> ProducerScope<T>.onRxObservableListener(
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

internal fun CompletableEmitter.onRxCompletableListener(exception: Exception?) {
    when {
        isDisposed -> return
        exception != null -> onError(exception)
        else -> onComplete()
    }
}

/*** FireStore Extension ***/
internal inline fun <reified T> SingleEmitter<T>.onFireStoreCompleteListener(
    task: Task<DocumentSnapshot>
) {
    try {
        val data = task.result?.toObject(T::class.java)
        onRxSingleListener(task.exception, data)
    } catch (ex: Exception) {
        onError(ex)
    }
}

internal inline fun <reified T> SingleEmitter<List<T>>.onFireStoreListCompleteListener(
    task: Task<QuerySnapshot>
) {
    try {
        val data = task.result?.toObjects(T::class.java)
        onRxSingleListener(task.exception, data)
    } catch (ex: Exception) {
        onError(ex)
    }
}

internal inline fun <reified T> ObservableEmitter<List<T>>.onFireStoreSnapshotListener(
    exception: Exception?,
    task: QuerySnapshot?
) {
    try {
        val data = task?.toObjects(T::class.java)
        onRxObservableListener(exception, data)
    } catch (ex: Exception) {
        onError(ex)
    }
}

@ExperimentalCoroutinesApi
internal inline fun <reified T> ProducerScope<List<T>>.onFireStoreSnapshotListener(
    exception: Exception?,
    task: QuerySnapshot?
) {
    try {
        val data = task?.toObjects(T::class.java)
        onRxObservableListener(exception, data)
    } catch (ex: Exception) {
        close(ex)
    }
}

@ExperimentalCoroutinesApi
internal inline fun <reified T> ObservableEmitter<T>.onFireStoreSnapshotListener(
    exception: Exception?,
    task: DocumentSnapshot?
) {
    try {
        val data = task?.toObject(T::class.java)
        onRxObservableListener(exception, data)
    } catch (ex: Exception) {
        onError(ex)
    }
}

@ExperimentalCoroutinesApi
internal inline fun <reified T> ProducerScope<T>.onFireStoreSnapshotListener(
    exception: Exception?,
    task: DocumentSnapshot?
) {
    try {
        val data = task?.toObject(T::class.java)
        onRxObservableListener(exception, data)
    } catch (ex: Exception) {
        close(ex)
    }
}

/**
 * @throws Exception if map data fail.
 */
internal inline fun <reified T : Any> QuerySnapshot.getNotNullObjects(): List<T> {
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

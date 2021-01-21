package one.module.chat.sdk.data.firestore.utils

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.IllegalArgumentException
import java.lang.ref.WeakReference

internal object Ref {

    fun collection(path: Path): CollectionReference {
        val ref: Any = referenceFromPath(path)
        return if (ref is CollectionReference) {
            ref
        } else {
            throw IllegalArgumentException("$path is not Collection")
        }
    }

    fun document(path: Path): DocumentReference {
        val ref: Any = referenceFromPath(path)
        return if (ref is DocumentReference) {
            ref
        } else {
            throw IllegalArgumentException("$path is not Document")
        }
    }

    private fun referenceFromPath(path: Path): Any {
        var ref: Any = db().collection(path.first())
        for (i in 1 until path.size()) {
            val component: String = path[i].orEmpty()
            ref = if (ref is DocumentReference) {
                ref.collection(component)
            } else {
                (ref as CollectionReference).document(component)
            }
        }
        return ref
    }

    private var firebaseFireStoreInstance: WeakReference<FirebaseFirestore>? = null

    private fun db(): FirebaseFirestore {
        val savedFireStore = firebaseFireStoreInstance?.get()
        return if (savedFireStore != null) {
            savedFireStore
        } else {
            val fireStore = ChatFirebase.getFireStore()
            firebaseFireStoreInstance = WeakReference(fireStore)
            fireStore
        }
    }
}

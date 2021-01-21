package one.module.chat.sdk.data.firestore.utils

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference

internal object FireStoreChatRef {

    fun getUserCollection() = Ref.collection(
        Path(
            FIRE_STORE_CHAT_COLLECTION_USER
        )
    )

    fun getGroupCollection() = Ref.collection(
        Path(
            FIRE_STORE_CHAT_COLLECTION_GROUP
        )
    )

    fun getMessageCollection(groupId: String): CollectionReference {
        val path = getMessageCollectionPath(groupId)
        return Ref.collection(
            Path(
                path
            )
        )
    }

    fun getMessageMembersSeenAtDocument(groupId: String): DocumentReference {
        val path = getMessageMemberCollectionPath(groupId)
        return Ref.document(
            Path(
                path
            )
        )
    }

    fun getUserDocument(userId: String) = Ref.document(
        Path(
            listOf(FIRE_STORE_CHAT_COLLECTION_USER, userId)
        )
    )

    fun getGroupDocument(groupId: String) = Ref.document(
        Path(
            listOf(FIRE_STORE_CHAT_COLLECTION_GROUP, groupId)
        )
    )

    fun getMessageDocument(groupId: String, messageId: String): DocumentReference {
        val collectionPath = getMessageCollectionPath(groupId)
        return Ref.document(
            Path(
                listOf(collectionPath, messageId)
            )
        )
    }

    private fun getMessageCollectionPath(groupId: String) =
        FIRE_STORE_CHAT_COLLECTION_MESSAGE.replace(
            FIRE_STORE_CHAT_COLLECTION_MESSAGE_GROUP, groupId
        )

    private fun getMessageMemberCollectionPath(groupId: String) =
        FIRE_STORE_CHAT_COLLECTION_GROUP_SEEN_AT.replace(
            FIRE_STORE_CHAT_COLLECTION_MESSAGE_GROUP,
            groupId
        )
}

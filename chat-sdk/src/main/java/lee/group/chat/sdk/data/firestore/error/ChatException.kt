package lee.group.chat.sdk.data.firestore.error

abstract class ChatException(message: String? = null) : Throwable(message)

internal class InValidChatUserException(
    message: String? = "Current Chat User is Invalid"
) : ChatException(message)

internal class FireStoreDataNotExistException(
    message: String? = "FireStore Data is not exist"
) : ChatException(message)

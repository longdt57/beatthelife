package one.module.chat.sdk.data.model.event

import one.module.chat.sdk.data.model.ChatMessage

sealed class OneChatEvent

class MessageReceived(val chatMessage: ChatMessage) : OneChatEvent()

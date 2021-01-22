package lee.group.chat.sdk.data.model.event

import lee.group.chat.sdk.data.model.ChatMessage

sealed class OneChatEvent

class MessageReceived(val chatMessage: ChatMessage) : OneChatEvent()

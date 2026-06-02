package org.twinkletech.telikomai.model

data class ChatMessage(
    val message: String,
    val isUser: Boolean,
    val buttons: List<ChatAction> = emptyList(),
    val isTyping: Boolean = false
)
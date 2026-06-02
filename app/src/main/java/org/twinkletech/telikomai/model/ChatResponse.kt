package org.twinkletech.telikomai.model

data class ChatResponse(
    val message: String,
    val buttons: List<ChatAction> = emptyList()
)
package org.twinkletech.telikomai.notification

data class NotificationItem(
    val id: Int,
    val title: String,
    val message: String,
    val timeLabel: String,
    val cardType: CardType,
    val primaryButtonText: String,
    val secondaryButtonText: String
)

enum class CardType {
    WARNING,   // Purple/red tint — plan expiry
    ALERT,     // Green tint — balance low
    PROMO      // Dark blue — weekend special
}
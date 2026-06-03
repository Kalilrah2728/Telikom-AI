package org.twinkletech.telikomai.notification

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import org.twinkletech.telikomai.R

class NotificationAdapter(
    private val items: List<NotificationItem>,
    private val onPrimaryClick: (NotificationItem) -> Unit,
    private val onSecondaryClick: (NotificationItem) -> Unit
) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    inner class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvMessage: TextView = itemView.findViewById(R.id.tvMessage)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        val btnPrimary: AppCompatButton = itemView.findViewById(R.id.btnPrimary)
        val btnSecondary: Button = itemView.findViewById(R.id.btnSecondary)
        val cardRoot: View = itemView.findViewById(R.id.cardRoot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notification_card, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val item = items[position]

        holder.tvTitle.text = item.title
        holder.tvMessage.text = item.message
        holder.tvTime.text = item.timeLabel
        holder.btnPrimary.text = item.primaryButtonText
        holder.btnSecondary.text = item.secondaryButtonText

        // Set card background based on type
        val bgDrawable = when (item.cardType) {
            CardType.WARNING -> R.drawable.bg_card_warning
            CardType.ALERT   -> R.drawable.bg_card_alert
            CardType.PROMO   -> R.drawable.bg_card_promo
        }
        holder.cardRoot.setBackgroundResource(bgDrawable)

        // Set primary button color based on type
        val primaryBgDrawable = when (item.cardType) {
            CardType.WARNING -> R.drawable.bg_btn_primary_white
            CardType.ALERT   -> R.drawable.bg_btn_primary_green
            CardType.PROMO   -> R.drawable.bg_btn_primary_yellow
        }
        holder.btnPrimary.setBackgroundColor(primaryBgDrawable)

        val primaryTextColor = when (item.cardType) {
            CardType.WARNING -> ContextCompat.getColor(holder.itemView.context, R.color.white)
            CardType.ALERT   -> ContextCompat.getColor(holder.itemView.context, R.color.white)
            CardType.PROMO   -> ContextCompat.getColor(holder.itemView.context, R.color.white)
        }
        holder.btnPrimary.setTextColor(primaryTextColor)

        holder.btnPrimary.setOnClickListener { onPrimaryClick(item) }
        holder.btnSecondary.setOnClickListener { onSecondaryClick(item) }
    }

    override fun getItemCount(): Int = items.size
}
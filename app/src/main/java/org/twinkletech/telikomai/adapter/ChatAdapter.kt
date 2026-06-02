package org.twinkletech.telikomai.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.twinkletech.telikomai.R
import org.twinkletech.telikomai.assistants.model.ChatMessage

class ChatAdapter(
    private val messages: MutableList<ChatMessage>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val USER = 1
        const val BOT = 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].isUser) USER else BOT
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        return if (viewType == USER) {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user_message, parent, false)

            UserViewHolder(view)

        } else {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_bot_message, parent, false)

            BotViewHolder(view)
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {

        val item = messages[position]

        when (holder) {

            is UserViewHolder -> {
                holder.txtMessage.text = item.message
            }

            is BotViewHolder -> {
                holder.txtMessage.text = item.message
            }
        }
    }

    override fun getItemCount(): Int = messages.size

    class UserViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        val txtMessage: TextView =
            view.findViewById(R.id.txtUserMessage)
    }

    class BotViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        val txtMessage: TextView =
            view.findViewById(R.id.txtBotMessage)
    }
}
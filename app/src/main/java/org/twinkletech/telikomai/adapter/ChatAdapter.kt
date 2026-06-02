package org.twinkletech.telikomai.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.twinkletech.telikomai.R
import org.twinkletech.telikomai.model.ChatMessage

class ChatAdapter(
    private val messages: MutableList<ChatMessage>,
    private val onButtonClick: (String) -> Unit
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

            UserViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.item_user_message,
                        parent,
                        false
                    )
            )

        } else {

            BotViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.item_bot_message,
                        parent,
                        false
                    )
            )
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val item = messages[position]
        when(holder){
            is UserViewHolder -> { holder.questionMessages.text = item.message }
            is BotViewHolder -> {
                holder.txtMessage.text = Html.fromHtml(item.message, Html.FROM_HTML_MODE_LEGACY)
                if (item.isTyping) {
                    val animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.blink)
                    holder.txtMessage.startAnimation(animation)
                    holder.btnAction.visibility = View.GONE

                } else {
                    holder.txtMessage.clearAnimation()
                    if (item.buttons.isNotEmpty()) {
                        holder.btnAction.visibility = View.VISIBLE
                        holder.btnAction.text = item.buttons.first().title
                        holder.btnAction.setOnClickListener { onButtonClick(item.buttons.first().action) }
                    } else {
                        holder.btnAction.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun getItemCount() = messages.size

    class UserViewHolder(view: View)
        : RecyclerView.ViewHolder(view){

        val questionMessages: TextView =
            view.findViewById(R.id.txtUserMessages)
    }

    class BotViewHolder(view: View)
        : RecyclerView.ViewHolder(view){

        val txtMessage: TextView = view.findViewById(R.id.txtBotMessage)

        val btnAction: TextView = view.findViewById(R.id.btnAction)
    }
}
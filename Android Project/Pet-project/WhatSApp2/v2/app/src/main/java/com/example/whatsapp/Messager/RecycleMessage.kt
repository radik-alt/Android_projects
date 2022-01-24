package com.example.whatsapp.Messager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsapp.R

class RecycleMessage (listArr: ArrayList<String>, contexts: Context) : RecyclerView.Adapter<RecycleMessage.MessageViewHolder>() {

    var list: ArrayList<String>? = null
    var context: Context? = null
    init {
        list = listArr
        context = contexts
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        var MessageItem: View = LayoutInflater.from(context)
            .inflate(R.layout.message, parent, false)

        return MessageViewHolder(MessageItem)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.txtMess?.text = list?.get(position)
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }


    class MessageViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

        var txtMess: TextView? = null
        init {
            txtMess = itemView.findViewById(R.id.textMessage)
        }

    }

}
package com.example.okapi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fragmenttz.Interface.InterfacePosition
import com.example.fragmenttz.R

class AdapterPosition (context: Context, list: ArrayList<String>, private val clickList: InterfacePosition) : RecyclerView.Adapter<AdapterPosition.PositionHolder>() {

    private var context: Context? = null
    private var list:ArrayList<String> = ArrayList()

    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PositionHolder {
        var viewPerson: View = LayoutInflater.from(context).inflate(
            R.layout.adapter_position,
            parent, false)
        return PositionHolder(viewPerson)
    }

    override fun onBindViewHolder(holder: PositionHolder, position: Int) {
        holder.position.text = list.get(position)

        holder.itemView.setOnClickListener {
            clickList.onClick(list.get(position))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class PositionHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var position: TextView

        init {
            position = itemView.findViewById(R.id.position)
        }
    }
}
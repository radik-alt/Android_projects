package com.example.okapi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fragmenttz.Models
import com.example.fragmenttz.R

class AdapterInfo(context: Context, list: ArrayList<Models>) : RecyclerView.Adapter<AdapterInfo.InfoHolder>() {

    private var context: Context? = null
    private var list:ArrayList<Models> = ArrayList()

    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoHolder {
        var viewPerson: View = LayoutInflater.from(context).inflate(
            R.layout.adapter_info,
            parent, false)
        return InfoHolder(viewPerson)
    }

    override fun onBindViewHolder(holder: InfoHolder, position: Int) {
        holder.f_name.text = list.get(position).first_name
        holder.l_name.text = list.get(position).last_name
        holder.birthday.text = list.get(position).birthday
        holder.age.text = list.get(position).age.toString()
        holder.positions.text = list.get(position).position

    }

    override fun getItemCount(): Int {
        return list.size
    }


    class InfoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var f_name: TextView
        lateinit var l_name: TextView
        lateinit var birthday: TextView
        lateinit var age: TextView
        lateinit var positions: TextView


        init {
            f_name = itemView.findViewById(R.id.f_name)
            l_name = itemView.findViewById(R.id.l_name)
            birthday = itemView.findViewById(R.id.birthday)
            age = itemView.findViewById(R.id.age)
            positions = itemView.findViewById(R.id.positions)
        }

    }
}
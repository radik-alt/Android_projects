package com.example.okapi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fragmenttz.Interface.InterfacePerson
import com.example.fragmenttz.Models
import com.example.fragmenttz.R

class AdapterPerson (contexts: Context, lists: ArrayList<Models>, private val clickList: InterfacePerson) : RecyclerView.Adapter<AdapterPerson.HolderPerson>() {

    private var context: Context? = null
    private var list:ArrayList<Models> = ArrayList()

    init {
        context = contexts
        list = lists
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderPerson {
        var viewPerson: View = LayoutInflater.from(context).inflate(
            R.layout.adapter_person,
            parent, false)
        return HolderPerson(viewPerson)
    }

    override fun onBindViewHolder(holder: HolderPerson, position: Int) {
        holder.f_name.text = list.get(position).first_name
        holder.l_name.text = list.get(position).last_name
        holder.birthday.text = list.get(position).birthday

        holder.itemView.setOnClickListener {
            var f_name = list.get(position).first_name
            var l_name = list.get(position).last_name
            var birthday = list.get(position).birthday
            clickList.onClick(f_name, l_name, birthday)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class HolderPerson (itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var f_name: TextView
        lateinit var l_name: TextView
        lateinit var birthday: TextView

        init {
            f_name = itemView.findViewById(R.id.f_name)
            l_name = itemView.findViewById(R.id.l_name)
            birthday = itemView.findViewById(R.id.birthday)
        }

    }
}
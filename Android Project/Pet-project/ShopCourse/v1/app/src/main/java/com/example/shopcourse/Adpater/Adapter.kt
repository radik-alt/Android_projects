package com.example.shopcourse.Adpater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shopcourse.Category.Category
import com.example.shopcourse.R

class Adapter : RecyclerView.Adapter<Adapter.CategoryViewHolder> {

    private var context: Context
    private var category: ArrayList<String> = ArrayList()

    constructor(_context: Context) {
        context = _context
        category.add("Игры")
        category.add("Сайты")
        category.add("Языки")
        category.add("Прочее")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        var categoryItems: View = LayoutInflater.from(context)
            .inflate(R.layout.category_item, parent, false)

        return CategoryViewHolder(categoryItems)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.categoryTitile?.text = category.get(position)

        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return category.size
    }


    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var categoryTitile: TextView? = null
        init {
            categoryTitile = itemView.findViewById(R.id.categoryID)
        }
    }
}
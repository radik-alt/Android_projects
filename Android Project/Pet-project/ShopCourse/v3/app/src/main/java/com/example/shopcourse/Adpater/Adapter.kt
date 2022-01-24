package com.example.shopcourse.Adpater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.shopcourse.Category.Category
import com.example.shopcourse.MainActivity
import com.example.shopcourse.R

class Adapter : RecyclerView.Adapter<Adapter.CategoryViewHolder> {

    private var context: Context
    private var category: ArrayList<Category> = ArrayList()

    constructor(_context: Context, category: ArrayList<Category>) {
        context = _context
        this.category = category
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        var categoryItems: View = LayoutInflater.from(context)
            .inflate(R.layout.category_item, parent, false)

        return CategoryViewHolder(categoryItems)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.categoryTitile?.text = category.get(position).name

        holder.itemView.setOnClickListener {
            var main: MainActivity = MainActivity()
            var id : Int = category.get(position).item
            main.showCourseByCategory(id)
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
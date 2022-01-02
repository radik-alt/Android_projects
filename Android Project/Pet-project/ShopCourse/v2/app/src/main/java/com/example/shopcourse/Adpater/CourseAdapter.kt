package com.example.shopcourse.Adpater

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shopcourse.Card
import com.example.shopcourse.Category.Course
import com.example.shopcourse.R

class CourseAdapter(context: Context, list:ArrayList<Course>): RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    private var context: Context
    private var list:ArrayList<Course> = ArrayList()

    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        var courseItems: View = LayoutInflater.from(context).inflate(R.layout.course_item,
            parent, false)

        return CourseViewHolder(courseItems)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.title_course?.text = list.get(position).title
        holder.colorCourse?.setBackgroundColor(Color.parseColor(list.get(position).color))
        holder.date_course?.text = list.get(position).date
        holder.level_course?.text = list.get(position).level

        holder.itemView.setOnClickListener {
            var intent = Intent(context, Card::class.java)
            /*var option:ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(
                context as Activity?, Pair<View, String>(holder.title_course, "courseImage"))*/

            intent.putExtra("title", list.get(position).title)
            intent.putExtra("color", list.get(position).color)
            intent.putExtra("date", list.get(position).date)
            intent.putExtra("level", list.get(position).level)
            intent.putExtra("img", list.get(position).img)

            context.startActivity(intent, /*option.toBundle()*/)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var title_course: TextView? = null
        var level_course: TextView? = null
        var date_course: TextView? = null
        var colorCourse: LinearLayout? = null
        var pict: ImageView? = null
        init {
            title_course = itemView.findViewById(R.id.title_course)
            date_course = itemView.findViewById(R.id.date_course)
            level_course = itemView.findViewById(R.id.level_course)
            colorCourse = itemView.findViewById(R.id.linerBack)
            pict = itemView.findViewById(R.id.imgBack)
        }
    }

}
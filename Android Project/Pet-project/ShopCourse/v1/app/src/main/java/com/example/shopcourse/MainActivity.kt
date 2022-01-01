package com.example.shopcourse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopcourse.Category.Category
import com.example.shopcourse.Adpater.Adapter
import com.example.shopcourse.Adpater.CourseAdapter
import com.example.shopcourse.Category.Course

class MainActivity : AppCompatActivity() {

    private var recycle: RecyclerView? = null
    private var recycleCard: RecyclerView? = null
    private var fullCourse: ArrayList<Course> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        categorItem()
        categoryCourse()
    }

    fun categoryCourse () {
        var list:ArrayList<Course> = ArrayList()
        list.add(Course(1,"Java", "java", "10 января", "Junior", "#424345", 3))
        list.add(Course(2,"Full Stack", "unity", "12 февраля", "Middle", "#ffcb3d", 2))
        list.add(Course(3,"GameDev", "java", "28 апреля", "Продвинутый", "#4dbbff", 2))
        list.add(Course(4,"Kotlin", "unity", "7 февраля", "Middle", "#2b95ff", 1))
        list.add(Course(5,"C++", "java", "11 июля", "Junior", "#45ff0d", 2))

        fullCourse.addAll(list)

        var CardAdapte: CourseAdapter = CourseAdapter(this, list)
        var coursManager : RecyclerView.LayoutManager = LinearLayoutManager(this,
            RecyclerView.HORIZONTAL, false)

        recycleCard?.layoutManager = coursManager
        recycleCard?.adapter = CardAdapte
    }

    fun categorItem () {
        var adapter : Adapter = Adapter(this)
        var lmanager: RecyclerView.LayoutManager = LinearLayoutManager(this,
            RecyclerView.HORIZONTAL, false)

        recycle?.layoutManager = lmanager
        recycle?.adapter = adapter

    }

    fun showCourseByCategory (idCateg: Int) {

        var updateList: ArrayList<Course> = ArrayList()


    }

    fun init () {
        recycle = findViewById(R.id.rcv)
        recycleCard = findViewById(R.id.recycleCard)
    }
}
package com.example.shopcourse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopcourse.Category.Category
import com.example.shopcourse.Adpater.Adapter
import com.example.shopcourse.Adpater.CourseAdapter
import com.example.shopcourse.Category.Basket
import com.example.shopcourse.Category.Course

class MainActivity : AppCompatActivity() {

    private var recycle: RecyclerView? = null
    private var recycleCard: RecyclerView? = null
    private var fullCourse: ArrayList<Course> = ArrayList()
    private var list:ArrayList<Course> = ArrayList()
    private var adapter : Adapter? = null
    private var CardAdapte: CourseAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

        var category:ArrayList<Category> = ArrayList()
        category.add(Category(0, "Все"))
        category.add(Category(1, "Игры"))
        category.add(Category(2, "Сайты"))
        category.add(Category(3, "Языки"))
        category.add(Category(4, "Прочее"))

        categorItem(category)

        list.add(Course(1,"Java", "java", "10 января", "Junior", "#424345", 3))
        list.add(Course(2,"Full Stack", "unity", "12 февраля", "Middle", "#ffcb3d", 2))
        list.add(Course(3,"GameDev", "java", "28 апреля", "Продвинутый", "#4dbbff", 2))
        list.add(Course(4,"Kotlin", "unity", "7 февраля", "Middle", "#2b95ff", 1))
        list.add(Course(5,"C++", "java", "11 июля", "Junior", "#45ff0d", 2))

        categoryCourse(list)

    }

    fun categoryCourse (list: ArrayList<Course>) {

        fullCourse.addAll(list)

        CardAdapte = CourseAdapter(this, list)
        var coursManager : RecyclerView.LayoutManager = LinearLayoutManager(this,
            RecyclerView.HORIZONTAL, false)

        recycleCard?.layoutManager = coursManager
        recycleCard?.adapter = CardAdapte
    }

    fun categorItem (category: ArrayList<Category>) {

        adapter = Adapter(this, category)
        var lmanager: RecyclerView.LayoutManager = LinearLayoutManager(this,
            RecyclerView.HORIZONTAL, false)

        recycle?.layoutManager = lmanager
        recycle?.adapter = adapter
    }

    fun showCourseByCategory (idCategor: Int) {

        list.clear()
        list.addAll(fullCourse)

        if (idCategor == 0){
            CardAdapte?.notifyDataSetChanged()
            return
        }

        var filterList: ArrayList<Course> = ArrayList()
        for (c:Course in list){
            if (c.category == idCategor){
                filterList.add(c)
            }
        }

        list.clear()
        list.addAll(filterList)
        CardAdapte?.notifyDataSetChanged()
    }

    fun init () {
        recycle = findViewById(R.id.rcv)
        recycleCard = findViewById(R.id.recycleCard)
    }

    fun bask(view: View) {
        var intent: Intent = Intent(this, ListBasket::class.java)
        startActivity(intent)
    }
}
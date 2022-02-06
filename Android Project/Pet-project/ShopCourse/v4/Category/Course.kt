package com.example.shopcourse.Category

class Course (id:Int, title:String, img:String, date:String, level:String, color:String, category:Int) {

    var id: Int? = null
    var title:String? = null
    var img: String? = null
    var date: String? = null
    var level: String? = null
    var color: String? = null
    var category:Int? = null

    init {
        this.id = id
        this.title = title
        this.img = img
        this.date = date
        this.level = level
        this.color = color
        this.category = category
    }


}
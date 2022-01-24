package com.example.shopcourse.room

import androidx.room.Entity

@Entity (tableName = "catalogDB")
data class Catalog (
    var id: Int,
    var title:String,
    var img: String,
    var date: String,
    var level: String,
    var color: String,
    var category:Int
)

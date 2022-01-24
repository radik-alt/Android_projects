package com.example.shopcourse.Category

class Basket {

    var bask: MutableCollection<String> = mutableListOf()
    fun add(titile: String) {
        bask.add(titile)
    }
}
package com.example.shopcourse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.shopcourse.Category.Basket

class ListBasket : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_basket)

        val Bask: Basket = Basket()
        var set: MutableCollection<String> = Bask.bask

        var list_item : ListView = findViewById(R.id.list_item)
        list_item.adapter = ArrayAdapter<String>(this, android.R.layout.activity_list_item, set.toTypedArray())
    }

    fun home(view: View) {
        val intentHome: Intent = Intent(this, MainActivity::class.java)
        startActivity(intentHome)
    }

}
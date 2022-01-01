package com.example.shopcourse

import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.shopcourse.Adpater.CourseAdapter

class Card : AppCompatActivity() {

    private var linerCard: RelativeLayout? = null
    private var titleCard: TextView? = null
    private var dateCard: TextView? = null
    private var levelCard: TextView? = null
    private var CardImage: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)
        init()
        setContent()

    }

    fun setContent() {
        var intent: Intent = getIntent()
        var title: String? = intent.getStringExtra("title")
        var date: String? = intent.getStringExtra("date")
        var level: String? = intent.getStringExtra("level")
        var color: String? = intent.getStringExtra("color")
        var pict: Int? = intent.getIntExtra("img", 0)

        linerCard?.setBackgroundColor(Color.parseColor(color))
        titleCard?.text = title
        dateCard?.text = date
        levelCard?.text = level

        var id: Int = Resources.getSystem().getIdentifier("",
            "drawable", this.packageName)
        CardImage?.setImageResource(id)
    }

    fun init() {
        linerCard = findViewById(R.id.linerCard)
        titleCard = findViewById(R.id.titleCard)
        dateCard = findViewById(R.id.dateCard)
        levelCard = findViewById(R.id.levelCard)
        CardImage = findViewById(R.id.CardImage)
    }
}
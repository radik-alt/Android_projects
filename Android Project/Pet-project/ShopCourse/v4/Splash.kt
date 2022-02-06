package com.example.shopcourse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class Splash : AppCompatActivity() {

    var handler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        handler.postDelayed(Runnable {
            run {
                val start: Intent = Intent(this, MainActivity::class.java)
                startActivity(start)
            }
        }, 2000)
    }
}
package com.example.shopcourse.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyService : Service() {

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        task()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    fun task() {

        Thread(Runnable {
            run {
                for (i in 0..10){
                    Log.d("MyService Task: ", "Service ${i}");
                    Thread.sleep(1000)
                }
            }
        }).start()

    }
}
package com.example.shopcourse.LifeData

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

class MyObserv : LifecycleObserver {

    @OnLifecycleEvent (Lifecycle.Event.ON_ANY)
    fun myRun (source: LifecycleOwner, event: Lifecycle.Event) {
        Log.d("TAGSSSS", event.toString())
    }
}
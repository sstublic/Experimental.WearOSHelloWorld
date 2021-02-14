package com.example.helloworld

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.wearable.activity.WearableActivity
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import androidx.wear.ambient.AmbientModeSupport
import com.example.helloworld.service.AppModel
import com.example.helloworld.service.CounterService

class MainActivity : FragmentActivity() {

    private lateinit var appModel: AppModel
    val tag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appModel = HelloWorldApp.getAppModel()

        appModel.serverConnected.observe(this, Observer {
            findViewById<TextView>(R.id.serviceStatusText)
                .text = if (it) "Service connected" else "Please wait, connecting...";
            findViewById<ProgressBar>(R.id.indeterminateBar)
                .visibility = if (it) View.INVISIBLE else View.VISIBLE
        })
        appModel.counter.observe(this, Observer {
            findViewById<TextView>(R.id.counterText)
                .text = "Counter: $it"
            Log.i(tag, "Counter: $it.")
        })
   }
}
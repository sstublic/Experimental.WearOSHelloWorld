package com.example.helloworld

import android.app.Application
import android.content.Intent
import android.util.Log
import com.example.helloworld.service.AppModel
import com.example.helloworld.service.CounterService

class HelloWorldApp : Application() {
    companion object {
        private val appModel = AppModel()
        fun getAppModel(): AppModel {
            return appModel;
        }
    }

    override fun onCreate() {
        Intent(this, CounterService::class.java).also { intent ->
            startService(intent)
        }
        Log.i("HelloWorldApp", "hello")
        super.onCreate()
    }
}
package com.example.helloworld.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.helloworld.HelloWorldApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CounterService : Service() {

    private lateinit var appModel: AppModel
    val tag = "CounterService"

    override fun onCreate() {
        super.onCreate()
        appModel = HelloWorldApp.getAppModel()
        appModel.serverConnected.postValue(false);
        Log.i(tag, "Service created")
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(tag, "Service started")

        val onCreateScope = CoroutineScope(Dispatchers.Default)

        // update counter if service is connected
        onCreateScope.launch {
            while (true) {
                delay(1000);

                if (!(appModel.serverConnected.value ?: false))
                    continue;

                var newValue = (appModel.counter.value ?: -1) + 1;
                appModel.counter.postValue(newValue)

                Log.i(tag, "Posted new value $newValue.")
            }
        }

        // toggle service connection on and off
        onCreateScope.launch {
            while (true) {
                delay(5000)
                var isConnected = appModel.serverConnected.value ?: false;
                appModel.serverConnected.postValue(!isConnected);
                Log.i(tag, "Setting service connected status to ${!isConnected}")
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}
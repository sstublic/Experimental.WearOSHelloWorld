package com.example.helloworld.service

import androidx.lifecycle.MutableLiveData

class AppModel {
    var counter = MutableLiveData<Int>()
    var serverConnected = MutableLiveData<Boolean>().apply { postValue(false) }
}

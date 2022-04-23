package com.rkhvstnv.dishrecipes.ui.activities.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(): ViewModel() {
    val test: MutableLiveData<String> = MutableLiveData("test")
}
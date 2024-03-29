package com.example.androidintro.ui.age

//import android.arch.lifecycle.LiveData
//import android.arch.lifecycle.MutableLiveData
//import android.arch.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AgeViewModel : ViewModel() {

    private val noReminders = MutableLiveData<String>().apply {
        value = "Your age in:"
    }
    val text: LiveData<String> = noReminders
}
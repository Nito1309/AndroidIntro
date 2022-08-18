package com.example.androidintro

import android.annotation.SuppressLint
import android.app.Application
import com.example.androidintro.data.UserSession
import com.example.androidintro.ui.home.ContactViewModel

class AndroidIntroApplication : Application() {

    companion object{
        lateinit var userSession : UserSession
        var contactData = ArrayList<ContactViewModel>()
    }
    override fun onCreate() {
        super.onCreate()
        userSession = UserSession(applicationContext)
    }
}
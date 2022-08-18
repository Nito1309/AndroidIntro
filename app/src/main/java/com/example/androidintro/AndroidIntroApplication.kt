package com.example.androidintro

import android.annotation.SuppressLint
import android.app.Application
import com.example.androidintro.data.UserSession

class AndroidIntroApplication : Application() {

    companion object{
        lateinit var userSession : UserSession
    }
    override fun onCreate() {
        super.onCreate()
        userSession = UserSession(applicationContext)
    }
}
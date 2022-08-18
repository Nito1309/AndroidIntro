package com.example.androidintro.data

import android.content.Context

class UserSession(val context : Context) {
    val SHARED_PREFS_NAME = "UserSession"
    val SHARED_EMAIL = "Email"
    val storage = context.getSharedPreferences(SHARED_PREFS_NAME, 0)

    fun saveLogin(email:String){
        storage.edit().putString(SHARED_EMAIL, email).apply()
    }

    fun getEmail():String{ return storage.getString(SHARED_EMAIL, "")!!}

    fun logout(){ storage.edit().clear().apply()}
}
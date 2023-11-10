package com.example.backside.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager (context: Context) {
    var sharedPreferences : SharedPreferences? = null
    var editor : SharedPreferences.Editor? = null

    // variabel key
    val keyPreference = "login"
    val keyName = "Email"
    val keyPassword = "Password"

    init {
        sharedPreferences = context.getSharedPreferences(keyPreference, Context.MODE_PRIVATE)
        editor = sharedPreferences?.edit()
    }

    // login
    fun sessionLogin(name: String, pass: String) {
        editor?.putString(keyName, name)
        editor?.putString(keyPassword, pass)
        editor?.apply()
    }

    // logout
    fun sessionLogout(){
        editor?.remove(keyName)
        editor?.remove(keyPassword)
        editor?.apply()
    }

    // validasi
    val name: String?
        get() = sharedPreferences?.getString(keyName, null)

    val pass: String?
        get() = sharedPreferences?.getString(keyPassword, null)

    fun isLogin() : Boolean {
        if (!name.isNullOrEmpty() && !pass.isNullOrEmpty()){
            return true
        }
            return false
    }
}
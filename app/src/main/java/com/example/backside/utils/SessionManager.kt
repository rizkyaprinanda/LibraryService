
package com.example.backside.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager (context: Context) {
    var sharedPreferences: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null

    // variabel key
    val keyPreference = "login"
    val keyName = "Email"

    init {
        sharedPreferences = context.getSharedPreferences(keyPreference, Context.MODE_PRIVATE)
        editor = sharedPreferences?.edit()
    }

    // login
    fun sessionLogin(name: String) {
        editor?.putString(keyName, name)
        editor?.apply()
    }

    // logout
    fun sessionLogout() {
        editor?.remove(keyName)
        editor?.apply()
    }

    // validasi
    val name: String?
        get() = sharedPreferences?.getString(keyName, null)


    fun isLogin(): Boolean {
        if (!name.isNullOrEmpty()) {
            return true
        }
        return false
    }


}
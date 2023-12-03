package com.example.backside.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.backside.MainMenuActivity
import com.example.backside.R
import com.example.backside.databinding.ActivityLoginBinding
import com.example.backside.databinding.ActivityRequestTokenBinding
import com.example.backside.utils.SessionManager
import com.example.backside.view.auth.LoginActivity

class RequestTokenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRequestTokenBinding
    private lateinit var preferences: SharedPreferences
    companion object {
        private const val RC_SIGN_IN = 123
        private const val PREF_NAME = "MyPreferences"
        private const val KEY_FIRST_TIME = "isFirstTime"
        private const val KEY_DARK_MODE = "isDarkMode"
        private const val KEY_JOINED = "isJoined"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestTokenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = getSharedPreferences(RequestTokenActivity.PREF_NAME, Context.MODE_PRIVATE)
        val isJoined = preferences.getBoolean(RequestTokenActivity.KEY_JOINED, false)
        val isLogin = preferences.getBoolean("isLogin", true)

        val sessionManager = SessionManager(this)


        binding.btnJoinToHome.setOnClickListener {
            preferences.edit().putBoolean(RequestTokenActivity.KEY_JOINED, true).apply()
            startActivity(Intent(this, MainMenuActivity::class.java))
            finish()
        }

    }
}
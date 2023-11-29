package com.example.backside.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.backside.databinding.ActivityGettingStartedBinding
import com.example.backside.view.auth.LoginActivity

class GettingStartedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGettingStartedBinding
    private val SPLASH_TIMEOUT: Long = 1000
    private lateinit var preferences: SharedPreferences

    companion object {
        private const val RC_SIGN_IN = 123
        private const val PREF_NAME = "MyPreferences"
        private const val KEY_FIRST_TIME = "isFirstTime"
        private const val KEY_DARK_MODE = "isDarkMode"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGettingStartedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = getSharedPreferences(GettingStartedActivity.PREF_NAME, Context.MODE_PRIVATE)

        val isFirstTime = preferences.getBoolean(GettingStartedActivity.KEY_FIRST_TIME, true)

        if (isFirstTime) {
            Handler().postDelayed({
                startActivity(Intent(this@GettingStartedActivity, GettingStarted2Activity::class.java))
                finish()
            }, SPLASH_TIMEOUT)
        } else {
            Handler().postDelayed({
                startActivity(Intent(this@GettingStartedActivity, LoginActivity::class.java))
                finish()
            }, SPLASH_TIMEOUT)
        }




    }
}

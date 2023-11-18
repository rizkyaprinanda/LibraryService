package com.example.backside

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.backside.databinding.ActivityGettingStarted4Binding
import com.example.backside.ui.auth.LoginActivity

class GettingStarted4Activity : AppCompatActivity() {

    private lateinit var binding: ActivityGettingStarted4Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGettingStarted4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        saveFirstTimePreference()

        binding.btnGetStarted.setOnClickListener {
            startLoginActivity()
        }
    }

    private fun saveFirstTimePreference() {
        val preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
        preferences.edit {
            putBoolean("isFirstTime", false)
        }
    }

    private fun startLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}

package com.example.backside

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class ResetPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        val backButton: ImageButton = findViewById(R.id.backButton)
        val confirmPasswordResetButton: Button = findViewById(R.id.confirmPasswordResetButton)

        backButton.setOnClickListener{ finish() }
    }
}
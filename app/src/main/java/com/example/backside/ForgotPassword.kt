package com.example.backside

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class ForgotPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val sendResetEmailButton: Button = findViewById(R.id.sendResetEmail)
        val backButton: ImageButton = findViewById(R.id.backButton)

        sendResetEmailButton.setOnClickListener{ startActivity(Intent(this@ForgotPassword, EmailSent::class.java)) }
        backButton.setOnClickListener{ finish() }
    }
}
package com.example.backside

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class EmailSent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_sent)

        val doneButton: Button = findViewById(R.id.doneButton)
        val backButton: ImageButton = findViewById(R.id.backButton)

        doneButton.setOnClickListener { setContentView(R.layout.activity_reset_password) }
        backButton.setOnClickListener{ finish() }
    }
}
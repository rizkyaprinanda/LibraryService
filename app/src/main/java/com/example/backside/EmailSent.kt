package com.example.backside

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.backside.databinding.ActivityEmailSentBinding
import com.example.backside.view.auth.LoginActivity

class EmailSent : AppCompatActivity() {
    private lateinit var binding: ActivityEmailSentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailSentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.doneButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }
}

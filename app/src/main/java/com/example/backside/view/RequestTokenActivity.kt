package com.example.backside.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.backside.MainMenuActivity
import com.example.backside.R
import com.example.backside.databinding.ActivityLoginBinding
import com.example.backside.databinding.ActivityRequestTokenBinding

class RequestTokenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRequestTokenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestTokenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnJoinToHome.setOnClickListener {
            startActivity(Intent(this, MainMenuActivity::class.java))
            finish()
        }

    }
}
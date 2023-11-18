package com.example.backside

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.backside.databinding.ActivityGettingStarted3Binding
import com.example.backside.databinding.ActivityGettingStartedBinding
import com.example.backside.ui.auth.LoginActivity

class GettingStarted3Activity : AppCompatActivity() {

    private lateinit var binding: ActivityGettingStarted3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGettingStarted3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ibNextSlide3.setOnClickListener{
            val intent = Intent(this, GettingStarted4Activity::class.java)
            startActivity(intent)
            finish()
        }

    }
}

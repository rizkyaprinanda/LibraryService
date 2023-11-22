package com.example.backside.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.backside.databinding.ActivityGettingStarted3Binding

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

package com.example.backside

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.backside.databinding.ActivityGettingStarted2Binding

class GettingStarted2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityGettingStarted2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGettingStarted2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ibNextSlide2.setOnClickListener {
            val intent = Intent(this, GettingStarted3Activity::class.java)
            startActivity(intent)
            finish()
        }

    }
}
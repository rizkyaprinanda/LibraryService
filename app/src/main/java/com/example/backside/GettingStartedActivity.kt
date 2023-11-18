package com.example.backside

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.backside.databinding.ActivityGettingStartedBinding

class GettingStartedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGettingStartedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGettingStartedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ibNextSlide.setOnClickListener{
            val intent = Intent(this, GettingStarted2Activity::class.java)
            startActivity(intent)
            finish()
        }

    }
}

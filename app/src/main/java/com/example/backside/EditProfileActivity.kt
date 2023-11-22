package com.example.backside

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

@kotlin.Suppress("DEPRECATION")
class EditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        val kembali = findViewById<ImageView>(R.id.kembali)
        //kembali
        kembali.setOnClickListener {
            onBackPressed()
        }
    }
}
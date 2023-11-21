@file:Suppress("DEPRECATION")

package com.example.backside

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.backside.R

class CreateVotingUserActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_voting_user)
        val kembali = findViewById<ImageView>(R.id.kembali)
        //kembali
        kembali.setOnClickListener {
            onBackPressed()
        }
        val data = listOf("Semua", "Romance", "Fiksi Sejarah", "Dongeng", "Aksi")
        val spinner: Spinner = findViewById(R.id.kategorispinner)
        val adapter = ArrayAdapter(this, R.layout.custom_spinner2, data)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val uploadFrame: FrameLayout = findViewById(R.id.upoadgambar)

        uploadFrame.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        val voteButton = findViewById<Button>(R.id.createbutton)
        voteButton.setOnClickListener {
            restart()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val imageView: ImageView = findViewById(R.id.imageView2)
        val textDragDrop: TextView = findViewById(R.id.teks)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri: Uri? = data.data
            imageView.setImageURI(imageUri)
            val parentLayout: FrameLayout = findViewById(R.id.upoadgambar)
            parentLayout.removeView(textDragDrop)
            imageView.layoutParams.width = FrameLayout.LayoutParams.WRAP_CONTENT
            imageView.layoutParams.height = FrameLayout.LayoutParams.WRAP_CONTENT
        }
    }

    private fun restart() {
        val message = "Berhasil Buat Voting"
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

        Handler().postDelayed({
            recreate()
        }, 1000)
    }
}

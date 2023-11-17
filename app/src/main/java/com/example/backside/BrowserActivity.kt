package com.example.backside

import BrowserAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BrowserActivity : AppCompatActivity() {
    companion object{
        val INTENT_PARCELABLE = "OBJECT_INTENT"
    }

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browser)

        val data = listOf("Semua","Romance", "Fiksi Sejarah", "Dongeng", "Aksi")
        val spinner: Spinner = findViewById(R.id.spinner)
        val search = findViewById<EditText>(R.id.search)
        val adapcher = ArrayAdapter(this, R.layout.custom_spinner, data)

        adapcher.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapcher





        val book = listOf<Books>(
            Books(R.drawable.gambar,
                "Luka Kata",
                "Candra Malik",
                "Romance",
                "0",
                false,
            ),
            Books(R.drawable.gambar5,
                "Cantik Itu Luka",
                "Eka Kurniawan",
                "Romance",
                "1",
                false,
            ),
            Books(R.drawable.gambar2,
                "Salt To The Sea",
                "Ruta Sepetys",
                "Fiksi Sejarah",
                "0",
                false,
            ),
            Books(R.drawable.gambar4,
                "House Of Shadows",
                "Nicola Cornick",
                "Fiksi Sejarah",
                "0",
                false
            ),
            Books(R.drawable.gambarku,
                "All The Light We Cannot See",
                "Nicola Cornick",
                "Fiksi Sejarah",
                "0",
                false
            ),
            Books(R.drawable.gambarku,
                "All The Light We Cannot See",
                "Nicola Cornick",
                "Aksi",
                "0",
                false
            ),



            )

        val adapter = BrowserAdapter(this, book)
        search.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {
                adapter.filter(charSequence.toString())
                println(charSequence.toString())
            }override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                println(charSequence.toString())
                adapter.filter(charSequence.toString())

            }
            override fun afterTextChanged(editable: Editable) {
                adapter.filter(editable.toString())
                println(editable.toString())
            }
        })
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                // Aksi yang diambil saat item dipilih
                val selectedItem = data[position]
                Toast.makeText(applicationContext, "Kategori dipilih: $selectedItem", Toast.LENGTH_SHORT).show()
                // Ambil nilai dari Spinner saat dipilih
                val selectedCategory = spinner.selectedItem.toString()

// Filter data sesuai dengan nilai Spinner
                val filteredData = when (selectedCategory) {
                    "Romance" -> book.filter { it.kategori == "Romance" }
                    "Fiksi Sejarah" -> book.filter { it.kategori == "Fiksi Sejarah" }
                    "Aksi" -> book.filter { it.kategori == "Aksi" }
                    // Tambahkan kondisi sesuai dengan kategori yang Anda miliki
                    else -> book // Default, tampilkan semua data
                }

                adapter.setData(filteredData)

            }


            override fun onNothingSelected(parentView: AdapterView<*>) {
                // Handle nothing selected if needed
            }
        }

        //Aksi pencarian

        // Asumsi Anda memiliki RecyclerView dengan id 'recyclerView' di layout activity_browser
        val recyclerView: RecyclerView = findViewById(R.id.rvBrowser)

        // Buat instance adapter dan berikan list buku ke dalamnya


        // Set adapter untuk RecyclerView
        recyclerView.adapter = adapter

        // Set layout manager ke GridLayoutManager
        recyclerView.layoutManager = GridLayoutManager(this, 2) // Ganti dengan jumlah kolom yang diinginkan
    }



}
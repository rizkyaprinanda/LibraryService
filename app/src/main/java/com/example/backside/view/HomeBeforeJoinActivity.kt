package com.example.backside.view

import InstitutionsAdapter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.backside.R
import com.example.backside.model.Institutions

class HomeBeforeJoinActivity : AppCompatActivity() {

    private lateinit var institutionsAdapter: InstitutionsAdapter
    private lateinit var originalData: List<Institutions>
    private lateinit var noResultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_before_join)

        val recyclerView: RecyclerView = findViewById(R.id.rvinstitution)
        val search: EditText = findViewById(R.id.edtSearch)
        noResultTextView = findViewById(R.id.tvNoResult)
        noResultTextView.visibility = View.GONE

        // Contoh data institusi
        val institutionList: List<Institutions> = listOf(
            Institutions("1", R.drawable.itb1, "Universitas A", "Bandung", "Perpustakaan Umum Kampus A", "access_token_1"),
            Institutions("2", R.drawable.ipb1, "Universitas B", "Bogor", "Perpustakaan Umum Kampus B", "access_token_2"),
            Institutions("3", R.drawable.sma1a, "SMA 1 A", "Jakarta", "Perpustakaan Umum SMA A", "access_token_3"),
            Institutions("3", R.drawable.sma1b, "SMA 1 B", "Jakarta", "Perpustakaan Umum SMA B", "access_token_4"),
            // Tambahkan data institusi lainnya sesuai kebutuhan
        )

        originalData = institutionList
        institutionsAdapter = InstitutionsAdapter(this, institutionList)

        // Mengganti LinearLayoutManager menjadi GridLayoutManager
        recyclerView.layoutManager = GridLayoutManager(this, 2) // Sesuaikan jumlah kolom sesuai keinginan
        recyclerView.adapter = institutionsAdapter

        search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {
                institutionsAdapter.filter(charSequence.toString())
                println(charSequence.toString())
            }

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                println(charSequence.toString())
                val query = charSequence.toString()
                performSearch(query)
            }

            override fun afterTextChanged(editable: Editable) {
                institutionsAdapter.filter(editable.toString())
                println(editable.toString())
            }
        })
    }

    private fun performSearch(query: String) {
        val filteredData = originalData.filter { it.name.contains(query, ignoreCase = true) }
        if (filteredData.isEmpty()) {
            noResultTextView.visibility = View.VISIBLE
            institutionsAdapter.setData(emptyList())
        } else {
            noResultTextView.visibility = View.GONE
            institutionsAdapter.setData(filteredData)
        }
    }
}

package com.example.backside

import PurchasedAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class PurchasedFragment : Fragment() {
    companion object {
        fun newInstance(): PurchasedFragment {
            return PurchasedFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_purchased, container, false)

        // Isi dengan logika yang ada dalam BrowserActivity

        val data = listOf("Semua","Romance", "Fiksi Sejarah", "Dongeng", "Aksi")
        val spinner: Spinner = view.findViewById(R.id.spinner)
        val search = view.findViewById<EditText>(R.id.search)
        val adapcher = ArrayAdapter<String>(requireContext(), R.layout.custom_spinner, data)



        adapcher.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapcher





        val book = listOf<PurchasedBooks>(
            PurchasedBooks(
                R.drawable.gambar4,
                "House Of Shadows",
                "Nicola Cornick",
                "Fiksi Sejarah",
                "50",
                false,
                false,
                "dfjsklasdjfklasdjl;fjasdl;jfsddkljasf;dljfasdkl;jf4fdkljfdlaskfelhahdfhasdjkfh4uhaksjdhfkajsdhflkj4hkljashdfjklahlkh34u8ytrytrfahsdkfhadksjfhasdjklfhlasdhfkladh"
            ),
            PurchasedBooks(R.drawable.gambar2,
                "Salt To The Sea",
                "Ruta Sepetys",
                "Fiksi Sejarah",
                "5",
                true,
                true,
                "dfjsklasdjfklasdjl;fjasdl;jfsddkljasf;dljfasdkl;jf4fdkljfdlaskfelhahdfhasdjkfh4uhaksjdhfkajsdhflkj4hkljashdfjklahlkh34u8ytrytrfahsdkfhadksjfhasdjklfhlasdhfkladh"
            ),
            PurchasedBooks(R.drawable.gambar,
                "Luka Kata",
                "Candra Malik",
                "Romance",
                "0",
                false,
                false,
                "dfjsklasdjfklasdjl;fjasdl;jfsddkljasf;dljfasdkl;jf4fdkljfdlaskfelhahdfhasdjkfh4uhaksjdhfkajsdhflkj4hkljashdfjklahlkh34u8ytrytrfahsdkfhadksjfhasdjklfhlasdhfkladh"
            ),
            PurchasedBooks(R.drawable.gambar5,
                "Cantik Itu Luka",
                "Eka Kurniawan",
                "Romance",
                "10",
                true,
                true,
                "dfjsklasdjfklasdjl;fjasdl;jfsddkljasf;dljfasdkl;jf4fdkljfdlaskfelhahdfhasdjkfh4uhaksjdhfkajsdhflkj4hkljashdfjklahlkh34u8ytrytrfahsdkfhadksjfhasdjklfhlasdhfkladh"
            )


            )

        val adapter = PurchasedAdapter(requireContext(), book) // Gunakan requireContext()

        search.addTextChangedListener(object : TextWatcher {
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
                Toast.makeText(requireContext(), "Kategori dipilih: $selectedItem", Toast.LENGTH_SHORT).show()

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

        val recyclerView: RecyclerView = view.findViewById(R.id.rvPurchase)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Isi dengan logika yang ada dalam BrowserActivity
        // Referensi view menggunakan 'view'
    }
}
package com.example.backside

import BrowserAdapter
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater

import android.view.ViewGroup
import android.widget.ArrayAdapter


class BrowserFragment : Fragment() {
    companion object {
        fun newInstance(): BrowserFragment {
            return BrowserFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_browser, container, false)

        // Isi dengan logika yang ada dalam BrowserActivity

        val data = listOf("Semua","Romance", "Fiksi Sejarah", "Dongeng", "Aksi")
        val spinner: Spinner = view.findViewById(R.id.spinner)
        val search = view.findViewById<EditText>(R.id.search)
        val adapcher = ArrayAdapter<String>(requireContext(), R.layout.custom_spinner, data)


        adapcher.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapcher





        val book = listOf<Books>(
            Books(R.drawable.gambar,
                "Luka Kata",
                "Candra Malik",
                "Romance",
                "0",
                false,
                "sdfhasdkhfasdhfkhasdjkfhasdjkhfkdshfkjahsdfjkhasdjkhfkehkuckdrfdfasdfmadfdsjfklasdjlfjasdlkfjsdfjklsdjfklsdjklfjsdklfjsdklfjkldfjeukycsdjkfhjekreuhkshdfjkhdjf"
            ),
            Books(R.drawable.gambar5,
                "Cantik Itu Luka",
                "Eka Kurniawan",
                "Romance",
                "1",
                false,
                "sdfhasdkhfasdhfkhasdjkfhasdjkhfkdshfkjahsdfjkhasdjkhfkehkuckdrfdfasdfmadfdsjfklasdjlfjasdlkfjsdfjklsdjfklsdjklfjsdklfjsdklfjkldfjeukycsdjkfhjekreuhkshdfjkhdjf"
            ),
            Books(R.drawable.gambar2,
                "Salt To The Sea",
                "Ruta Sepetys",
                "Fiksi Sejarah",
                "0",
                false,
                "sdfhasdkhfasdhfkhasdjkfhasdjkhfkdshfkjahsdfjkhasdjkhfkehkuckdrfdfasdfmadfdsjfklasdjlfjasdlkfjsdfjklsdjfklsdjklfjsdklfjsdklfjkldfjeukycsdjkfhjekreuhkshdfjkhdjf"
            ),
            Books(R.drawable.gambar4,
                "House Of Shadows",
                "Nicola Cornick",
                "Fiksi Sejarah",
                "0",
                false,
                "sdfhasdkhfasdhfkhasdjkfhasdjkhfkdshfkjahsdfjkhasdjkhfkehkuckdrfdfasdfmadfdsjfklasdjlfjasdlkfjsdfjklsdjfklsdjklfjsdklfjsdklfjkldfjeukycsdjkfhjekreuhkshdfjkhdjf"
            ),
            Books(R.drawable.gambarku,
                "All The Light We Cannot See",
                "Nicola Cornick",
                "Fiksi Sejarah",
                "0",
                false,
                "sdfhasdkhfasdhfkhasdjkfhasdjkhfkdshfkjahsdfjkhasdjkhfkehkuckdrfdfasdfmadfdsjfklasdjlfjasdlkfjsdfjklsdjfklsdjklfjsdklfjsdklfjkldfjeukycsdjkfhjekreuhkshdfjkhdjf"
            ),
            Books(R.drawable.gambarku,
                "All The Light We Cannot See",
                "Nicola Cornick",
                "Aksi",
                "0",
                false,
                "sdfhasdkhfasdhfkhasdjkfhasdjkhfkdshfkjahsdfjkhasdjkhfkehkuckdrfdfasdfmadfdsjfklasdjlfjasdlkfjsdfjklsdjfklsdjklfjsdklfjsdklfjkldfjeukycsdjkfhjekreuhkshdfjkhdjf"
            ),



            )

        val adapter = BrowserAdapter(requireContext(), book) // Gunakan requireContext()

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

        val recyclerView: RecyclerView = view.findViewById(R.id.rvBrowser)
        recyclerView.adapter = adapter

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter.setOnItemClickListener { book ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("selected_book", book)
            startActivity(intent)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Isi dengan logika yang ada dalam BrowserActivity
        // Referensi view menggunakan 'view'
    }



}


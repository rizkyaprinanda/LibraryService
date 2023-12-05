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
import com.example.backside.model.Books


@Suppress("NAME_SHADOWING")
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
        val adapcher = ArrayAdapter(requireContext(), R.layout.custom_spinner, data)


        adapcher.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapcher





        val book = listOf(
            Books(
                R.drawable.gambar,
                "Luka Kata",
                "Candra Malik",
                "Romance",
                "0",
                false,
                "Sebuah kisah cinta yang penuh dengan kata-kata indah dan menyentuh hati. Di dalamnya, Candra Malik menggambarkan lika-liku percintaan antara dua karakter utama yang penuh dengan konflik dan kebahagiaan.",
                7.2
            ), Books(
                R.drawable.gambar,
                "Luka Kata",
                "Candra Malik",
                "Romance",
                "0",
                false,
                "Kisah cinta yang melibatkan pertarungan batin dan perjuangan untuk mencari arti sejati dari kata-kata. Sebuah perjalanan emosional yang memikat pembaca hingga halaman terakhir.",
                7.2
            ), Books(
                R.drawable.gambar5,
                "Cantik Itu Luka",
                "Eka Kurniawan",
                "Romance",
                "1",
                false,
                "Sebuah novel epik yang mengeksplorasi kecantikan dan penderitaan. Eka Kurniawan berhasil menciptakan dunia yang penuh warna dengan karakter-karakter yang tak terlupakan.",
                8.5
            ), Books(
                R.drawable.gambar2,
                "Salt To The Sea",
                "Ruta Sepetys",
                "Fiksi Sejarah",
                "0",
                false,
                "Sebuah kisah tragis tentang perjalanan melintasi lautan selama Perang Dunia II. Ruta Sepetys dengan cemerlang menuliskan pengalaman para karakter dengan penuh empati.",
                9.0
            ), Books(
                R.drawable.gambar4,
                "House Of Shadows",
                "Nicola Cornick",
                "Fiksi Sejarah",
                "0",
                false,
                "Rumah berhantu yang menyimpan misteri sepanjang masa. Nicola Cornick mengajak pembaca untuk menelusuri setiap sudut rumah tersebut dalam perjalanan yang penuh teka-teki.",
                8.2
            ), Books(
                R.drawable.gambarku,
                "All The Light We Cannot See",
                "Anthony Doerr",
                "Fiksi Sejarah",
                "0",
                false,
                "Sebuah kisah indah tentang kehidupan selama Perang Dunia II. Anthony Doerr berhasil menangkap esensi keajaiban di tengah kegelapan.",
                9.5
            ), Books(
                R.drawable.gambarku,
                "All The Light We Cannot See",
                "Anthony Doerr",
                "Aksi",
                "0",
                false,
                "Sebuah cerita aksi yang memacu adrenalin. Anthony Doerr menggabungkan elemen-elemen thriller dengan nuansa sejarah yang mendalam.",
                8.0
            )
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

// Filter data sesuai dengan nilai Spinner
                val filteredData = when (spinner.selectedItem.toString()) {
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

}


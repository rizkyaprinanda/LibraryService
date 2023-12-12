package com.example.backside

import com.example.backside.adapters.PurchasedAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class PurchasedFragment : Fragment() {
    companion object {
        fun newInstance(): PurchasedFragment {
            return PurchasedFragment()
        }
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
        val adapcher = ArrayAdapter(requireContext(), R.layout.custom_spinner, data)
        var doubleBackPressedOnce = false

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!doubleBackPressedOnce) {
                    Toast.makeText(requireContext(), "Press again to exit", Toast.LENGTH_SHORT).show()
                    doubleBackPressedOnce = true
                    Handler(Looper.getMainLooper()).postDelayed({
                        doubleBackPressedOnce = false
                    }, 2000)
                } else {
                    requireActivity().finish()
                }
            }
        })



        adapcher.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapcher





        val book = listOf(
            PurchasedBooks(
                imgBook = R.drawable.gambar4,
                judul = "House Of Shadows",
                penulis = "Nicola Cornick",
                kategori = "Fiksi Sejarah",
                jumlah = 50,
                sudahVote = false,
                sudahTerbeli = false,
                deskripsi = "Sebuah kisah fiksi sejarah yang menggali rumah berhantu dengan lapisan misteri di setiap sudutnya. Melalui penulisan Nicola Cornick, pembaca akan diajak untuk meresapi atmosfir zaman dulu dan menyelidiki rahasia yang tersembunyi dalam bayang-bayang rumah tersebut.",
                rating = 7.0
            ),
            PurchasedBooks(
                imgBook = R.drawable.gambar2,
                judul = "Salt To The Sea",
                penulis = "Ruta Sepetys",
                kategori = "Fiksi Sejarah",
                jumlah = 5,
                sudahVote = true,
                sudahTerbeli = true,
                deskripsi = "Sebuah perjalanan epik melintasi laut pada masa Perang Dunia II. Ruta Sepetys membawa pembaca ke dalam kehidupan empat orang yang terjebak dalam kisah dramatis kapal karam Wilhelm Gustloff. Pengalaman pahit dan getir perang terungkap melalui mata para karakter yang berusaha bertahan hidup.",
                rating = 8.1
            ),
            PurchasedBooks(
                imgBook = R.drawable.gambar,
                judul = "Luka Kata",
                penulis = "Candra Malik",
                kategori = "Romance",
                jumlah = 0,
                sudahVote = false,
                sudahTerbeli = false,
                deskripsi = "Kisah romantis yang mengisahkan perjalanan dua hati yang saling terluka. Candra Malik dengan indah menyampaikan konflik emosional dan keindahan cinta melalui kata-kata yang menyentuh. Novel ini mengajak pembaca untuk menjelajahi dunia perasaan yang dalam dan penuh makna.",
                rating = 7.5
            ),
            PurchasedBooks(
                imgBook = R.drawable.gambar5,
                judul = "Cantik Itu Luka",
                penulis = "Eka Kurniawan",
                kategori = "Romance",
                jumlah = 10,
                sudahVote = true,
                sudahTerbeli = true,
                deskripsi = "Eka Kurniawan menghadirkan kisah penuh warna tentang kecantikan dan luka di sepanjang perjalanan hidup seorang wanita. Dengan sentuhan magis dalam bahasa penceritaannya, novel ini mengeksplorasi kompleksitas hubungan dan keindahan yang muncul dari setiap lukisan kata.",
                rating = 8.3
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

        val recyclerView: RecyclerView = view.findViewById(R.id.rvPurchase)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        return view
    }

}
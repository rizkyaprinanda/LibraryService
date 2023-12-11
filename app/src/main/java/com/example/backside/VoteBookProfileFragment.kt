package com.example.backside

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.backside.adapters.BrowserAdapter
import com.example.backside.model.Books


class VoteBookProfileFragment : Fragment() {
    companion object {
        fun newInstance(): VoteBookProfileFragment {
            return VoteBookProfileFragment()
        }
    }




        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            val view = inflater.inflate(R.layout.fragment_vote_book_profile, container, false)

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
                ),
                )

            val adapter = BrowserAdapter(requireContext(), book) // Gunakan requireContext()

            //Aksi pencarian

            val recyclerView: RecyclerView = view.findViewById(R.id.rvVoteProfile)
            recyclerView.adapter = adapter

            val layoutManager = GridLayoutManager(requireContext(), 2)
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return 1
                }
            }


            recyclerView.layoutManager = layoutManager




            return view
        }



}
package com.example.backside

import com.example.backside.adapters.HomeRekomendasiAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.backside.model.Books


class HomeFragment : Fragment() {
    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
//        return inflater.inflate(R.layout.fragment_home, container, false)

        val book = listOf(
            Books(
                imgBook = R.drawable.gambar,
                judul = "Luka Kata",
                penulis = "Candra Malik",
                kategori = "Romance",
                jumlah = "0",
                sudahVote = false,
                deskripsi = "Sebuah kisah cinta yang penuh dengan kata-kata indah dan menyentuh hati. Di dalamnya, Candra Malik menggambarkan lika-liku percintaan antara dua karakter utama yang penuh dengan konflik dan kebahagiaan.",
                rating = 7.2
        ), Books(
                imgBook = R.drawable.gambar,
                judul = "Luka Kata",
                penulis = "Candra Malik",
                kategori = "Romance",
                jumlah = "0",
                sudahVote = false,
                deskripsi = "Kisah cinta yang melibatkan pertarungan batin dan perjuangan untuk mencari arti sejati dari kata-kata. Sebuah perjalanan emosional yang memikat pembaca hingga halaman terakhir.",
                rating = 7.2
        ), Books(
                imgBook = R.drawable.gambar5,
                judul = "Cantik Itu Luka",
                penulis = "Eka Kurniawan",
                kategori = "Romance",
                jumlah = "1",
                sudahVote = false,
                deskripsi = "Sebuah novel epik yang mengeksplorasi kecantikan dan penderitaan. Eka Kurniawan berhasil menciptakan dunia yang penuh warna dengan karakter-karakter yang tak terlupakan.",
                rating = 8.5
        ), Books(
                imgBook = R.drawable.gambar2,
                judul = "Salt To The Sea",
                penulis = "Ruta Sepetys",
                kategori = "Fiksi Sejarah",
                jumlah = "0",
                sudahVote = false,
                deskripsi = "Sebuah kisah tragis tentang perjalanan melintasi lautan selama Perang Dunia II. Ruta Sepetys dengan cemerlang menuliskan pengalaman para karakter dengan penuh empati.",
                rating = 9.0
        ), Books(
                imgBook = R.drawable.gambar4,
                judul = "House Of Shadows",
                penulis = "Nicola Cornick",
                kategori = "Fiksi Sejarah",
                jumlah = "0",
                sudahVote = false,
                deskripsi = "Rumah berhantu yang menyimpan misteri sepanjang masa. Nicola Cornick mengajak pembaca untuk menelusuri setiap sudut rumah tersebut dalam perjalanan yang penuh teka-teki.",
                rating = 8.2
        ), Books(
                imgBook = R.drawable.gambarku,
                judul = "All The Light We Cannot See",
                penulis = "Anthony Doerr",
                kategori = "Fiksi Sejarah",
                jumlah = "0",
                sudahVote = false,
                deskripsi = "Sebuah kisah indah tentang kehidupan selama Perang Dunia II. Anthony Doerr berhasil menangkap esensi keajaiban di tengah kegelapan.",
                rating = 9.5
        ), Books(
                imgBook = R.drawable.gambarku,
                judul = "All The Light We Cannot See",
                penulis = "Anthony Doerr",
                kategori = "Aksi",
                jumlah = "0",
                sudahVote = false,
                deskripsi = "Sebuah cerita aksi yang memacu adrenalin. Anthony Doerr menggabungkan elemen-elemen thriller dengan nuansa sejarah yang mendalam.",
                rating = 8.0
        )




        )

        val adapter = HomeRekomendasiAdapter(requireContext(), book) // Gunakan requireContext()



        val recyclerView: RecyclerView = view.findViewById(R.id.recycler1)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)


        val recyclerView2: RecyclerView = view.findViewById(R.id.recycler2)
        recyclerView2.adapter = adapter
        recyclerView2.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)


        return view
    }

}
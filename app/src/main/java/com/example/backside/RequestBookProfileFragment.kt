package com.example.backside

import com.example.backside.adapters.PurchasedAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class RequestBookProfileFragment : Fragment() {
    companion object {
        fun newInstance(): RequestBookProfileFragment {
            return RequestBookProfileFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_request_book_profile, container, false)
        val book = listOf(
            PurchasedBooks(
                img = R.drawable.gambar4,
                judul = "House Of Shadows",
                penulis = "Nicola Cornick",
                kategori = "Fiksi Sejarah",
                jumlah = 50,
                sudahVote = false,
                sudahTerbeli = true,

                deskripsi = "Sebuah kisah fiksi sejarah yang menggali rumah berhantu dengan lapisan misteri di setiap sudutnya. Melalui penulisan Nicola Cornick, pembaca akan diajak untuk meresapi atmosfir zaman dulu dan menyelidiki rahasia yang tersembunyi dalam bayang-bayang rumah tersebut.",
                rating = 7.0,
                lingambar = "https://retoolapi.dev/UWAqiN"

            ),
            PurchasedBooks(
                img = R.drawable.gambar2,
                judul = "Salt To The Sea",
                penulis = "Ruta Sepetys",
                kategori = "Fiksi Sejarah",
                jumlah = 5,
                sudahVote = true,
                sudahTerbeli = true,
                deskripsi = "Sebuah perjalanan epik melintasi laut pada masa Perang Dunia II. Ruta Sepetys membawa pembaca ke dalam kehidupan empat orang yang terjebak dalam kisah dramatis kapal karam Wilhelm Gustloff. Pengalaman pahit dan getir perang terungkap melalui mata para karakter yang berusaha bertahan hidup.",
                rating = 7.0,
                lingambar = "https://retoolapi.dev/UWAqiN/"
            ),
            PurchasedBooks(
                img= R.drawable.gambar,
                judul = "Luka Kata",
                penulis = "Candra Malik",
                kategori = "Romance",
                jumlah = 0,
                sudahVote = false,
                sudahTerbeli = false,
                deskripsi = "Kisah romantis yang mengisahkan perjalanan dua hati yang saling terluka. Candra Malik dengan indah menyampaikan konflik emosional dan keindahan cinta melalui kata-kata yang menyentuh. Novel ini mengajak pembaca untuk menjelajahi dunia perasaan yang dalam dan penuh makna.",
                rating = 7.0,
                lingambar = "https://retoolapi.dev/UWAqiN/"
            ),
            PurchasedBooks(
                img = R.drawable.gambar5,
                judul = "Cantik Itu Luka",
                penulis = "Eka Kurniawan",
                kategori = "Romance",
                jumlah = 10,
                sudahVote = true,
                sudahTerbeli = true,
                deskripsi = "Eka Kurniawan menghadirkan kisah penuh warna tentang kecantikan dan luka di sepanjang perjalanan hidup seorang wanita. Dengan sentuhan magis dalam bahasa penceritaannya, novel ini mengeksplorasi kompleksitas hubungan dan keindahan yang muncul dari setiap lukisan kata.",
                rating = 7.0,
                lingambar = "https://retoolapi.dev/UWAqiN/"
            ),



            )

        val adapter = PurchasedAdapter(requireContext(), book) // Gunakan requireContext()


        //Aksi pencarian

        val recyclerView: RecyclerView = view.findViewById(R.id.rvPurchaseProfile)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        return view
    }



}
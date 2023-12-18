package com.example.backside.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.backside.DetailActivity
import com.example.backside.PurchasedBooks
import com.example.backside.R
import com.example.backside.model.Books
import java.io.Serializable

class PurchasedAdapter(private val context: Context, private var purchaseList: List<PurchasedBooks>)
    : RecyclerView.Adapter<PurchasedAdapter.PurchasedViewHolder>() {

    private var originalBookList: List<PurchasedBooks> = purchaseList.toList()

    private var itemClickListener: ((PurchasedBooks) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchasedViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.items_purchased, parent, false)
        return PurchasedViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PurchasedViewHolder, position: Int) {
        val book = purchaseList[position]
        holder.bindView(book)
    }

    override fun getItemCount(): Int {
        return purchaseList.size
    }

    fun filter(query: String) {
        val filteredList = if (query.isEmpty() || query == " ") {
            // Jika query kosong, tampilkan semua data
            originalBookList

        } else {
            // Jika query tidak kosong, filter data berdasarkan query
            originalBookList.filter { book ->
                book.judul.contains(query, ignoreCase = true) ||
                        book.penulis.contains(query, ignoreCase = true) ||
                        book.kategori.contains(query, ignoreCase = true)
            }
        }

        // Update data set dalam adapter
        setData(filteredList)
    }

    fun setData(filteredData: List<PurchasedBooks>) {
        purchaseList = filteredData
        notifyDataSetChanged()
    }

    inner class PurchasedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var currentBook: PurchasedBooks
        private val imgbrow = view.findViewById<ImageView>(R.id.gambaritem)
        private val judul = view.findViewById<TextView>(R.id.judulbuku)
        private val terbeli = view.findViewById<TextView>(R.id.purchase)

        private val penulis = view.findViewById<TextView>(R.id.penulisbuku)

        private val jumlah = view.findViewById<TextView>(R.id.jumlah)
        private  val load = view.findViewById<Button>(R.id.load)



        init {
            load.setOnClickListener {
                // Pastikan currentBook telah diinisialisasi sebelumnya saat binding
                currentBook.let { book ->
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra("purchase_book", book as Serializable)
                    itemView.context.startActivity(intent)
                }
            }
        }




        @SuppressLint("SetTextI18n")
        fun bindView(book: PurchasedBooks) {
            currentBook = book
//            imgbrow.setImageResource(book.img.toInt())
            Glide.with(itemView)
                .load(book.lingambar) // Ganti dengan URL yang Anda dapatkan dari Google Drive
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                .placeholder(R.drawable.gambar2) // Placeholder gambar sementara sedang dimuat
                .error(R.drawable.gambar4) // Gambar yang ditampilkan saat ada kesalahan
                .into(imgbrow)

            judul.text = book.judul
            penulis.text = book.penulis
            terbeli.text = "Belum"

            jumlah.text = "${book.jumlah} Vote"

            if (!book.sudahTerbeli) {
                terbeli.text = "Not Purchased"
                terbeli.setTextColor(ContextCompat.getColor(context, R.color.red)) // Ganti dengan warna yang diinginkan
                jumlah.setTextColor(ContextCompat.getColor(context, R.color.red))
            } else {
                // Jika sudah terbeli, atur warna default (misalnya, hitam)
                terbeli.text = "Purchased"
                terbeli.setTextColor(ContextCompat.getColor(context, R.color.green)) // Ganti dengan warna default yang diinginkan
                jumlah.setTextColor(ContextCompat.getColor(context, R.color.green))
            }






            itemView.setOnClickListener {
                itemClickListener?.invoke(book)
            }
        }
    }
}

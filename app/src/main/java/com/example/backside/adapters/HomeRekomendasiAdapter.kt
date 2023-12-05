package com.example.backside.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.backside.DetailBookActivity
import com.example.backside.R
import com.example.backside.model.Books

class HomeRekomendasiAdapter(private val context: Context, private var bookList: List<Books>)
    : RecyclerView.Adapter<HomeRekomendasiAdapter.HomeRekomendasiViewHolder>() {


    private var itemClickListener: ((Books) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRekomendasiViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.items_homerekomendasi, parent, false)
        return HomeRekomendasiViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HomeRekomendasiViewHolder, position: Int) {
        val books = bookList[position]
        holder.bindView(books)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }



    @SuppressLint("NotifyDataSetChanged")
    fun setData(filteredData: List<Books>) {
        bookList = filteredData
        notifyDataSetChanged()
    }

    inner class HomeRekomendasiViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var currentBook: Books
        private val imgbrow = view.findViewById<ImageView>(R.id.gambarbrowser)
        private val judul = view.findViewById<TextView>(R.id.judulbrow)
        private val penulis = view.findViewById<TextView>(R.id.penulisbrow)
        private val kategori = view.findViewById<TextView>(R.id.kategoribrow)
        private val jumlah = view.findViewById<TextView>(R.id.jumlahvotebrow)
        private val rating = view.findViewById<TextView>(R.id.tvRating)
        private val mantepLayout = view.findViewById<LinearLayout>(R.id.mantep)
        private val layout = view.findViewById<LinearLayout>(R.id.layoutCardRecommendation)

        init {
            layout.setOnClickListener {
                // Pastikan currentBook telah diinisialisasi sebelumnya saat binding
                currentBook.let { book ->
                    val intent = Intent(itemView.context, DetailBookActivity::class.java)
                    intent.putExtra("purchase_book", book)
                    itemView.context.startActivity(intent)
                }
            }
        }




        fun bindView(book: Books) {
            currentBook = book
            imgbrow.setImageResource(book.imgBook)
            judul.text = book.judul
            penulis.text = book.penulis
            kategori.text = book.kategori
            jumlah.text = book.jumlah
            rating.text = book.rating.toString()


            if(!book.sudahVote){
                mantepLayout.setBackgroundResource(R.drawable.bgup)
            }else{
                mantepLayout.setBackgroundResource(R.drawable.bgupnew)
            }
            mantepLayout.setOnClickListener{




                if(!book.sudahVote){
                    book.jumlah = (book.jumlah.toInt() + 1).toString()
                    jumlah.text = book.jumlah
                    book.sudahVote = true
                    val message = "Berhasil up vote buku ${book.judul}"
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    mantepLayout.setBackgroundResource(R.drawable.bgupnew)
                }else{
                    book.jumlah = (book.jumlah.toInt() - 1).toString()
                    jumlah.text = book.jumlah
                    book.sudahVote = false
                    val message = "Yaah, kamu down vote buku ${book.judul}"
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    mantepLayout.setBackgroundResource(R.drawable.bgup)
                }
                itemView.setOnClickListener {
                    itemClickListener?.invoke(book)
                }


            }

        }
    }
}
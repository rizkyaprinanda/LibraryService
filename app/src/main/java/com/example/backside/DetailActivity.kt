package com.example.backside

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import android.widget.TextView
import com.example.backside.databinding.ActivityDetailBinding
import com.example.backside.model.Books


@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val image = findViewById<ImageView>(R.id.gambardetail)
        val juduldetail = findViewById<TextView>(R.id.juduldetail)
        val penulisdetail = findViewById<TextView>(R.id.penulisdetail)
        val deskripsi = findViewById<TextView>(R.id.deskripsi)
        val jumlah = findViewById<TextView>(R.id.jumlahvotedetail)
        val upvote = findViewById<FrameLayout>(R.id.butonvote)
        if (intent.hasExtra("selected_book")) {
            val selectedBook = intent.getParcelableExtra<Books>("selected_book")

            selectedBook?.let { book ->
                val bookTitle = book.judul
                val bookAuthor = book.penulis
                val jumlahbook = book.jumlah
                val bookImage = book.imgBook
                val bookDeskripsi = book.deskripsi

//
//                val titleTextView: TextView = findViewById(R.id.titleTextView)
//                val authorTextView: TextView = findViewById(R.id.authorTextView)
//                val categoryTextView: TextView = findViewById(R.id.categoryTextView)

                juduldetail.text = bookTitle
                jumlah.text = jumlahbook
                penulisdetail.text = bookAuthor
                deskripsi.text = bookDeskripsi
                image.setImageResource(bookImage)

                upvote.setOnClickListener {
                    selectedBook.let{ book ->

                //                        val index = books!!.indexOfFirst { it.judul == book.judul }
                //                        if (index != -1) {
                //                            books[index].jumlah = (books[index].jumlah.toInt() + 1).toString()
                //                        }

                        if(book.sudahVote){
                            book.jumlah = (book.jumlah.toInt() - 1).toString()
                            jumlah.text = book.jumlah
                            book.sudahVote = false
                        }else{
                            book.jumlah = (book.jumlah.toInt() + 1).toString()
                            jumlah.text = book.jumlah
                            book.sudahVote = true
                        }







                        Toast.makeText(this, "Berhasil upvote buku ${book.judul}", Toast.LENGTH_SHORT).show()

                    }

                }

                // Jika Anda menggunakan gambar, tampilkan gambar buku di ImageView
                // val bookImageView: ImageView = findViewById(R.id.bookImageView)
                // bookImageView.setImageResource(bookImage)

            }

        }else{
            val selectedBook = intent.getParcelableExtra<PurchasedBooks>("purchase_book")

            selectedBook?.let { book ->
                val bookTitle = book.judul
                val bookAuthor = book.penulis
                val jumlahbook = book.jumlah
                val bookImage = book.imgBook
                val bookDeskripsi = book.deskripsi

//
//                val titleTextView: TextView = findViewById(R.id.titleTextView)
//                val authorTextView: TextView = findViewById(R.id.authorTextView)
//                val categoryTextView: TextView = findViewById(R.id.categoryTextView)

                juduldetail.text = bookTitle
                jumlah.text = jumlahbook
                penulisdetail.text = bookAuthor
                deskripsi.text = bookDeskripsi
                image.setImageResource(bookImage)

                upvote.setOnClickListener {
                    selectedBook.let{ book ->

                //                        val index = books!!.indexOfFirst { it.judul == book.judul }
                //                        if (index != -1) {
                //                            books[index].jumlah = (books[index].jumlah.toInt() + 1).toString()
                //                        }

                        if(book.sudahVote){
                            book.jumlah = (book.jumlah.toInt() - 1).toString()
                            jumlah.text = book.jumlah
                            book.sudahVote = false
                        }else{
                            book.jumlah = (book.jumlah.toInt() + 1).toString()
                            jumlah.text = book.jumlah
                            book.sudahVote = true
                        }







                        Toast.makeText(this, "Berhasil upvote buku ${book.judul}", Toast.LENGTH_SHORT).show()

                    }

                }

                // Jika Anda menggunakan gambar, tampilkan gambar buku di ImageView
                // val bookImageView: ImageView = findViewById(R.id.bookImageView)
                // bookImageView.setImageResource(bookImage)

            }

        }

        binding.imgBack.setOnClickListener{
            onBackPressed()
        }

    }
}
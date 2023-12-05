package com.example.backside

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.backside.databinding.ActivityDetailBookBinding
import com.example.backside.model.Books


@Suppress("DEPRECATION")
class DetailBookActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBookBinding
    companion object {
        fun defaultBook(): Books {
            return Books(0, "", "", "", "", false, "", 0.0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val image = findViewById<ImageView>(R.id.gambardetail)
        val juduldetail = findViewById<TextView>(R.id.juduldetail)
        val penulisdetail = findViewById<TextView>(R.id.penulisdetail)
        val deskripsi = findViewById<TextView>(R.id.deskripsi)
        val jumlah = findViewById<TextView>(R.id.jumlahvotedetail)
        val rating = findViewById<TextView>(R.id.tvRating)


        val selectedBook: Books = intent.getParcelableExtra("purchase_book") ?: defaultBook()

        juduldetail.text = selectedBook.judul
        jumlah.text = selectedBook.jumlah
        penulisdetail.text = selectedBook.penulis
        deskripsi.text = selectedBook.deskripsi
        rating.text = selectedBook.rating.toString()
        image.setImageResource(selectedBook.imgBook)

        binding.butonvote.setOnClickListener {
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

        binding.imgBack.setOnClickListener{
            onBackPressed()
        }
    }
}

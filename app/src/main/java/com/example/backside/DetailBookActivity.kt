package com.example.backside

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.backside.databinding.ActivityDetailBookBinding
import com.example.backside.model.BooksCollections

class DetailBookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val upvote = findViewById<FrameLayout>(R.id.butonvote)

        val selectedBook = intent.getSerializableExtra("book") as? BooksCollections
        if (selectedBook != null) {
            val formattedRating = String.format("%.1f", selectedBook.rating)

            binding.tvBookTitle.text = selectedBook.title
            binding.tvAuthor.text = selectedBook.author
            binding.tvRating.text = formattedRating
            binding.tvCategory.text = selectedBook.genre
            binding.descriptionBook.text = selectedBook.description
            binding.bookCount.text = selectedBook.count.toString()

            val image = selectedBook.image

            Glide.with(this)
                .load(image)
                .fitCenter()
                .into(binding.detailImageBook)

            binding.imgBack.setOnClickListener {
                onBackPressed()
            }

            upvote.setOnClickListener {
                selectedBook.let { book ->
                    if (book.isVote) {
                        book.count = (book.count - 1)
                        binding.bookCount.text = book.count.toString()
                        book.isVote = false
                        Toast.makeText(this, "Oops, you downvoted the book ${selectedBook.title}", Toast.LENGTH_SHORT).show()
                    } else {
                        book.count = (book.count + 1)
                        binding.bookCount.text = book.count.toString()
                        book.isVote = true
                        Toast.makeText(this, "Successfully upvoted the book ${selectedBook.title}", Toast.LENGTH_SHORT).show()
                    }

                }
            }

        } else {
            val purchasedBook = intent.getParcelableExtra<PurchasedBooks>("purchase_book")

            purchasedBook?.let { book ->
                binding.tvBookTitle.text = book.judul
                binding.bookCount.text = book.jumlah.toString()
                binding.tvAuthor.text = book.penulis
                binding.descriptionBook.text = book.deskripsi
                Glide.with(this)
                    .load(book.imgBook)
                    .fitCenter()
                    .into(binding.detailImageBook)

                binding.imgBack.setOnClickListener {
                    onBackPressed()
                }

                upvote.setOnClickListener {
                    book.let { purchasedBook ->
                        if (purchasedBook.sudahVote) {
                            purchasedBook.jumlah = (purchasedBook.jumlah - 1)
                            binding.bookCount.text = purchasedBook.jumlah.toString()
                            purchasedBook.sudahVote = false
                            Toast.makeText(this, "Oops, you downvoted the book ${purchasedBook.judul}", Toast.LENGTH_SHORT).show()

                        } else {
                            purchasedBook.jumlah = (purchasedBook.jumlah + 1)
                            binding.bookCount.text = purchasedBook.jumlah.toString()
                            purchasedBook.sudahVote = true
                        }

                        Toast.makeText(this, "Successfully upvoted the book ${purchasedBook.judul}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

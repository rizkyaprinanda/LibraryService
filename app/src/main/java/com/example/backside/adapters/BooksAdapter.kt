package com.example.backside.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.backside.DetailBookActivity
import com.example.backside.R
import com.example.backside.model.BooksCollections
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.Serializable

class BooksAdapter(
    private val context: Context,
    private var booksList: List<BooksCollections>,
    private var itemClickListener: ((BooksCollections) -> Unit)? = null
) : RecyclerView.Adapter<BooksAdapter.BookHolder>() {

    private var originalBooksList: List<BooksCollections> = booksList.toList()

    class BookHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookTitle: TextView = itemView.findViewById(R.id.tvBookTitle)
        val bookAuthor: TextView = itemView.findViewById(R.id.tvAuthor)
        val bookRating: TextView = itemView.findViewById(R.id.tvRatingBooks)
        val genre: TextView = itemView.findViewById(R.id.tvCategory)
        val layoutCard: CardView = itemView.findViewById(R.id.cvBookImage)
        val layoutImageBook: LinearLayout = itemView.findViewById(R.id.layoutCard)
        val imgBook: ImageView = itemView.findViewById(R.id.ivBookImage)
        val upVote: LinearLayout = itemView.findViewById(R.id.btnUpVote)
        val bookCount: TextView = itemView.findViewById(R.id.tvBookCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.items_books, parent, false)
        return BookHolder(itemView)
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        val selectedBook = booksList[position]

        with(holder) {
            bookTitle.text = selectedBook.title
            bookAuthor.text = selectedBook.author
            bookRating.text = selectedBook.rating.toString()
            genre.text = selectedBook.genre
            bookCount.text = selectedBook.count.toString()

            val storage = Firebase.storage
            val storageRef = storage.reference

            val imageRef = selectedBook.image

            Glide.with(context)
                .asBitmap()
                .load(imageRef)
                .fitCenter()
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        imgBook.setImageBitmap(resource)
                    }
                })

            layoutImageBook.setOnClickListener {
                val intent = Intent(context, DetailBookActivity::class.java)
                intent.putExtra("book", selectedBook)
                context.startActivity(intent)
            }


            itemView.setOnClickListener {
                itemClickListener?.invoke(selectedBook)
            }

            if(!selectedBook.isVote){
                upVote?.setBackgroundResource(R.drawable.bgup)
            }else{
                upVote?.setBackgroundResource(R.drawable.bgupnew)
            }
            upVote?.setOnClickListener{ 
                if (!selectedBook.isVote) {
                    selectedBook.count++
                    selectedBook.isVote = true
                    notifyDataSetChanged()
                } else {
                    selectedBook.count--
                    selectedBook.isVote = false
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun getItemCount() = booksList.size

    fun filter(query: String) {
        val filteredList = if (query.isEmpty() || query == " ") {
            originalBooksList.toList()
        } else {
            originalBooksList.filter { book ->
                book.title.contains(query, ignoreCase = true) ||
                        book.description.contains(query, ignoreCase = true) ||
                        book.author.contains(query, ignoreCase = true)
            }
        }
        setData(filteredList)
    }

    fun setData(filteredData: List<BooksCollections>) {
        booksList = filteredData
        notifyDataSetChanged()
    }
}

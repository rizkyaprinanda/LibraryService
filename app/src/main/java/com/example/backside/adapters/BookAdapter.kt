package com.example.backside.adapters

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.backside.R
import com.example.backside.utils.BookItem

class BookAdapter(
    private val context: Context,
    private var dataList: List<BookItem>,
    private var itemClickListener: ((BookItem) -> Unit)? = null
) : RecyclerView.Adapter<BookAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle = view.findViewById<TextView>(R.id.tvBookTitle)
        val tvAuthor = view.findViewById<TextView>(R.id.tvAuthor)
        val tvCategory = view.findViewById<TextView>(R.id.tvCategory)
        val layoutCard = view.findViewById<LinearLayout>(R.id.layoutCard)
        val cvImageView = view.findViewById<CardView>(R.id.cvBookImage)
        val ivBookImage = view.findViewById<ImageView>(R.id.ivBookImage)
        val upVote = view.findViewById<LinearLayout>(R.id.llUpvote)
        val bookCount = view.findViewById<TextView>(R.id.tvBookCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.items_books, parent, false)
        return MyViewHolder(itemView)
    }

    private val MAX_DESCRIPTION_LENGTH = 150
    private val MAX_TITLE_LENGTH = 35

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val selectedBook = dataList[position]
        val volumeInfo = selectedBook.volumeInfo
        val categories = volumeInfo.categories
        val descriptionText = if (volumeInfo.title.length > MAX_TITLE_LENGTH) {
            volumeInfo.title.substring(0, MAX_TITLE_LENGTH) + "..."
        } else {
            volumeInfo.title
        }

        with(holder) {
            tvTitle.text = descriptionText
            tvAuthor.text = volumeInfo.publisher.orEmpty()
            tvCategory.text = categories.toString().removeSurrounding("[", "]")

            Glide.with(view)
                .asBitmap()
                .load(volumeInfo.imageLinks.thumbnail)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        // Set the image to ImageView
                        ivBookImage.setImageBitmap(resource)

                        // Get the dominant color from the image
                        val dominantColor = getDominantColor(resource)

                        // Set the background of cvImageView with the dominant color
                        cvImageView.setBackgroundColor(dominantColor)
                    }
                })

            layoutCard.setOnClickListener {
                Toast.makeText(context, volumeInfo.title, Toast.LENGTH_SHORT).show()
            }

            val upVoteBackgroundResId =
                if (selectedBook.sudahVote) R.drawable.bgupnew else R.drawable.bgup
            upVote.setBackgroundResource(upVoteBackgroundResId)

            upVote.setOnClickListener {

                if (!selectedBook.sudahVote) {
                    selectedBook.jumlah = 0.toString()
                    Log.d("BookAdapter", "selectedBook.jumlah before: ${selectedBook.jumlah}")
                    if (!selectedBook.jumlah.isNullOrBlank()) {

                        selectedBook.jumlah = (selectedBook.jumlah.toInt() + 1).toString()
                        Log.d("BookAdapter", "selectedBook.jumlah after: ${selectedBook.jumlah}")
                        holder.bookCount.text = selectedBook.jumlah
                    }

                    selectedBook.sudahVote = true
                    val message = "Successfully upvoted the book ${volumeInfo.title}"
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    upVote.setBackgroundResource(R.drawable.bgupnew)
                } else {
                    Log.d("BookAdapter", "selectedBook.jumlah before: ${selectedBook.jumlah}")
                    if (!selectedBook.jumlah.isNullOrBlank()) {
                        selectedBook.jumlah = (selectedBook.jumlah.toInt() - 1).toString()
                        Log.d("BookAdapter", "selectedBook.jumlah after: ${selectedBook.jumlah}")
                        holder.bookCount.text = selectedBook.jumlah
                    }



                    selectedBook.sudahVote = false
                    val message = "Oops, you downvoted the book ${volumeInfo.title}"
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    upVote.setBackgroundResource(R.drawable.bgup)
                }
            }

            itemView.setOnClickListener {
                // Memastikan itemClickListener tidak null sebelum dipanggil
                itemClickListener?.invoke(selectedBook)
            }


        }
    }

    private fun getDominantColor(bitmap: Bitmap?): Int {
        val newBitmap = bitmap?.let { Bitmap.createScaledBitmap(it, 1, 1, true) }
        return newBitmap?.getPixel(0, 0) ?: 0
    }

    override fun getItemCount(): Int = dataList.size

    fun setData(data: List<BookItem>) {
        dataList = data
        notifyDataSetChanged()
    }
}

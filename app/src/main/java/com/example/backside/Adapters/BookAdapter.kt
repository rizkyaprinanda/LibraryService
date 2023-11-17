package com.example.backside.Adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.backside.R
import com.example.backside.utils.BookItem

class BookAdapter(
    private val context: Context,
    private var dataList: List<BookItem>
) : RecyclerView.Adapter<BookAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle = view.findViewById<TextView>(R.id.tvBookTitle)
        val tvAuthor = view.findViewById<TextView>(R.id.tvAuthor)
        val tvCategory = view.findViewById<TextView>(R.id.tvCategory)
        val layoutCard = view.findViewById<LinearLayout>(R.id.layoutCard)
        val cvImageView = view.findViewById<CardView>(R.id.cvBookImage)
        val ivBookImage = view.findViewById<ImageView>(R.id.ivBookImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.items_books, parent, false)
        return MyViewHolder(itemView)
    }

    val MAX_DESCRIPTION_LENGTH = 150
    val MAX_TITLE_LENGTH = 35

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dataList[position]

        val description = currentItem.volumeInfo.description.orEmpty()

        val titleText = if (currentItem.volumeInfo.title.length > MAX_TITLE_LENGTH) {
            currentItem.volumeInfo.title.substring(0, MAX_TITLE_LENGTH) + "..."
        } else {
            currentItem.volumeInfo.title
        }

        val descriptionText = if (description.length > MAX_DESCRIPTION_LENGTH) {
            description.substring(0, MAX_DESCRIPTION_LENGTH) + "..."
        } else {
            description
        }

        val selectedBook = dataList[position]

        val volumeInfo = selectedBook.volumeInfo
        val categories = volumeInfo.categories


        holder.tvTitle.text = currentItem.volumeInfo.publisher
        holder.tvAuthor.text = titleText
        holder.tvCategory.text = currentItem.volumeInfo.categories.toString().removeSurrounding("[", "]")

        val image = currentItem.volumeInfo.imageLinks.thumbnail

        Glide.with(holder.view)
            .asBitmap()
            .load(image)
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    // Mengatur gambar ke ImageView
                    holder.ivBookImage.setImageBitmap(resource)

                    // Mendapatkan warna dominan dari gambar
                    val dominantColor = getDominantColor(resource)

                    // Mengatur latar belakang cvImageView dengan warna dominan
                    holder.cvImageView.setBackgroundColor(dominantColor)

                }
            })

        holder.layoutCard.setOnClickListener {
            Toast.makeText(context, currentItem.volumeInfo.title, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDominantColor(bitmap: Bitmap?): Int {
        val newBitmap = bitmap?.let { Bitmap.createScaledBitmap(it, 1, 1, true) }
        return newBitmap?.getPixel(0, 0) ?: 0
    }

    override fun getItemCount(): Int = dataList.size

    fun setData(data: List<BookItem>) {
        dataList = data.filter { it.volumeInfo?.description?.isNotBlank() == true }
        notifyDataSetChanged()
    }
}

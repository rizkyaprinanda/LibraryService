package com.example.backside.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.backside.R
import com.example.backside.ResponseItem
import com.example.backside.utils.BookItem
import com.example.backside.utils.BookResponse

class BookAdapter(
    private val context: Context,
    private val dataList: ArrayList<BookItem>
) : RecyclerView.Adapter<BookAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tvNama)
        val tvUsername = view.findViewById<TextView>(R.id.tvUsername)
        val tvEmail = view.findViewById<TextView>(R.id.tvEmail)
        val cvMain = view.findViewById<CardView>(R.id.cvMain)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.items_layout, parent, false)
        return MyViewHolder(itemView)
    }

    val MAX_DESCRIPTION_LENGTH = 150
    val MAX_TITLE_LENGTH = 35
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dataList[position]


        // Check if description is not null before accessing its length
        val description = currentItem.volumeInfo.description.orEmpty()

        // Batasi title menjadi maksimal 35 karakter
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

        holder.tvName.text = currentItem.volumeInfo.publisher
        holder.tvUsername.text = titleText
        holder.tvEmail.text = descriptionText

        val image = currentItem.volumeInfo.imageLinks.thumbnail
        Glide.with(holder.view)
            .load(image)

            .into(holder.view.findViewById<ImageView>(R.id.imageView))

        holder.cvMain.setOnClickListener {
            Toast.makeText(context, currentItem.volumeInfo.title, Toast.LENGTH_SHORT).show()
        }
    }


    override fun getItemCount(): Int = dataList.size

    // Hapus fungsi setData karena datalist sudah diinisialisasi di konstruktor

    fun setData(data: List<BookItem>) {
        dataList.clear()
        dataList.addAll(data.filter { it.volumeInfo?.description?.isNotBlank() == true })
        notifyDataSetChanged()
    }

}

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

class RVAdapter(
    private val context: Context,
    private val datalist: ArrayList<ResponseItem>// Ganti dengan List<ResponseItem>
) : RecyclerView.Adapter<RVAdapter.MyViewHolder>() {

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

    val MAX_OVERVIEW_LENGTH = 100
    val MAX_TITLE_LENGTH = 35
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = datalist[position]

        // Batasi overview menjadi maksimal 100 karakter
        val overviewText = if (currentItem.overview.length > MAX_OVERVIEW_LENGTH) {
            currentItem.overview.substring(0, MAX_OVERVIEW_LENGTH) + "..."
        } else {
            currentItem.overview
        }

        // Batasi title menjadi maksimal 35 karakter
        val titleText = if (currentItem.title.length > MAX_TITLE_LENGTH) {
            currentItem.title.substring(0, MAX_TITLE_LENGTH) + "..."
        } else {
            currentItem.title
        }

        val formattedRating = String.format("%.1f", currentItem.voteAverage)

        holder.tvName.text = "Rating \t$formattedRating"
        holder.tvUsername.text = titleText
        holder.tvEmail.text = overviewText

        val image = "https://image.tmdb.org/t/p/w500${currentItem.backdropPath}"
        Glide.with(holder.view)
            .load(image)
            .centerCrop()
            .into(holder.view.findViewById<ImageView>(R.id.imageView))



        holder.cvMain.setOnClickListener {
            Toast.makeText(context, "" + currentItem.title, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = datalist.size

    // Hapus fungsi setData karena datalist sudah diinisialisasi di konstruktor

    fun setData(data: List<ResponseItem>) {
        datalist.clear()
        datalist.addAll(data)
        notifyDataSetChanged()
    }


}

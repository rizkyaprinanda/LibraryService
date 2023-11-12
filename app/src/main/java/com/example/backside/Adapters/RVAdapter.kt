package com.example.backside.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.backside.R
import com.example.backside.ResponseModel

class RVAdapter(
    private val context: Context,
    private val datalist: ArrayList<ResponseModel>
) :RecyclerView.Adapter<RVAdapter.MyViewHolder>() {
    class MyViewHolder(val view: View): RecyclerView.ViewHolder(view){
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

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvName.text = datalist.get(position).name
        holder.tvUsername.text = datalist.get(position).username
        holder.tvEmail.text = datalist.get(position).email
        holder.cvMain.setOnClickListener{
            Toast.makeText(context, "" + datalist.get(position).username, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = datalist.size

    fun setData(data: ArrayList<ResponseModel>){
        datalist.clear()
        datalist.addAll(data)
        notifyDataSetChanged()
    }
}
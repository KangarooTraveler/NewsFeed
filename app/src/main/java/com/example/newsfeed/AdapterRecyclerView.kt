package com.example.newsfeed

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsfeed.models.Articles
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AdapterRecyclerView(var context: Context) :
    RecyclerView.Adapter<AdapterRecyclerView.MyViewHolder>() {

    private var myList = emptyList<Articles>()

    //Инициализация объектов и классов
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val secondFragment = DescriptionFragment()
        val args = Bundle()
        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)!!
        val tvDate = itemView.findViewById<TextView>(R.id.tvDate)!!
        val imageView = itemView.findViewById<ImageView>(R.id.ivNews)!!
        val cardView = itemView.findViewById<CardView>(R.id.cardViewRowResult)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        )
    }

    //Присвоение сериализации к объектам
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvTitle.text = myList[position].title
        holder.tvDate.text = convertResponseDate(myList[position].publishedAt)
        if (myList[position].urlToImage.isNullOrEmpty())
            holder.cardView.isVisible = false
        else {
            holder.cardView.isVisible = true
            Glide.with(context)
                .load(myList[position].urlToImage)
                .into(holder.imageView)
        }

        holder.itemView.setOnClickListener {
            if (!myList[position].url.isNullOrEmpty()) {
                holder.args.putString("url", myList[position].url)
                holder.secondFragment.arguments = holder.args
                (context as MainActivity)
                    .supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, holder.secondFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    //Конвертация даты
    private fun convertResponseDate(date: String): String {
        val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val output = SimpleDateFormat("dd.MM.yyyy · HH:mm")
        var d: Date? = null
        try {
            d = input.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return output.format(d)
    }

    fun setData(newList: List<Articles>) {
        myList = newList
        notifyDataSetChanged()
    }

}
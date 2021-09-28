package com.mynews.metronews.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mynews.metronews.R
import com.mynews.metronews.model.Article
import com.mynews.metronews.ui.ApiNewsDetail

class CategoryAdapter(private val newsList: List<Article>, private val context: Context) :
    RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.category_row_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder.description.text = newsList[position].description
        holder.title.text = newsList[position].title
        Glide.with(context).load(newsList[position].urlToImage).centerCrop()
            .into(holder.thumbnail)

        holder.cardView.setOnClickListener {

            Log.d("MYTITLE", newsList[position].title!!)
            val intent = Intent(context, ApiNewsDetail::class.java)
            intent.putExtra("title", newsList[position].title)
            intent.putExtra("description", newsList[position].content)
            intent.putExtra("thumbnail", newsList[position].urlToImage)
            intent.putExtra("source", newsList[position].source!!.name)
            intent.putExtra("url",newsList[position].url)
            context.startActivity(intent)
        }
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cardView = itemView.findViewById(R.id.category_row_item) as CardView
        // val description = itemView.findViewById(R.id.category_news_des) as TextView
        val thumbnail = itemView.findViewById(R.id.category_news_image) as ImageView
        val title = itemView.findViewById(R.id.news_title) as TextView
    }
}
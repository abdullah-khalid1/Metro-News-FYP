package com.mynews.metronews.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mynews.metronews.R
import com.mynews.metronews.model.Post


class HomeAdapter(
    private val context: Context,
    private val list: ArrayList<Post>
) : RecyclerView.Adapter<HomeAdapter.HomeHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_post_item, parent, false)
        return HomeHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {

        val title = list[position].title
        val description = list[position].description
        val image_url = list[position].downloadUrlImage
        val date = list[position].date

        holder.title.text = title
        holder.description.text = description
        holder.date.text = date
//        holder.description.text = description
        Glide.with(context).load(image_url).centerCrop()
            .into(holder.imageView)

        holder.cardview.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("news_title", title)
            bundle.putString("news_description", description)
            bundle.putString("news_image_url", image_url)
            bundle.putString("news_date", date)

            it.findNavController().navigate(R.id.Detail, bundle)
        }
    }


    class HomeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cardview = itemView.findViewById(R.id.row_post_item) as CardView
        val title = itemView.findViewById(R.id.txt_title) as TextView
        val description = itemView.findViewById(R.id.txt_description_1) as TextView
        val imageView = itemView.findViewById(R.id.image_news) as ImageView
        val date = itemView.findViewById(R.id.post_date) as TextView


    }
}
package com.mynews.metronews.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mynews.metronews.R
import com.mynews.metronews.model.Video
import com.mynews.metronews.ui.NewsVideoPlayer

class VideoAdapter(private val context: Context, private val arrayList: ArrayList<Video>) :
    RecyclerView.Adapter<VideoAdapter.MyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.video_row_item, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        Glide.with(context).load(arrayList[position].videoDownloadUrl).centerCrop()
            .into(holder.thumbnail)
        holder.title.text = arrayList[position].title

        holder.cardview.setOnClickListener {
            val intent = Intent(context, NewsVideoPlayer::class.java)
            intent.putExtra("url", arrayList[position].videoDownloadUrl)
            context.startActivity(intent)
        }
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnail = itemView.findViewById(R.id.thumbnail_for_video) as ImageView
        val title = itemView.findViewById(R.id.title_for_video) as TextView
        val cardview = itemView.findViewById(R.id.video_row_item) as CardView
    }
}
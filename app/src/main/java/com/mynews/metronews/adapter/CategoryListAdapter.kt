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
import com.mynews.metronews.model.Category
import com.mynews.metronews.ui.NewsApiActivity

class CategoryListAdapter(
    private val context: Context,
    private val arrayList: ArrayList<Category>
) :
    RecyclerView.Adapter<CategoryListAdapter.MyOwnHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyOwnHolder {

        val view =
            LayoutInflater.from(context).inflate(R.layout.category_list_row_item, parent, false)

        return MyOwnHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: MyOwnHolder, position: Int) {
        holder.textTitle.text = arrayList[position].name
     //   holder.thumbnail.setImageResource(arrayList[position].image)

        Glide.with(context).load(arrayList[position].image).into(holder.thumbnail)

        holder.cardView.setOnClickListener {

            val intent = Intent(context, NewsApiActivity::class.java)
            intent.putExtra("value", position)
            context.startActivity(intent)
        }

    }

    class MyOwnHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textTitle = itemView.findViewById(R.id.category_list_title) as TextView
        val thumbnail = itemView.findViewById(R.id.img_card_id) as ImageView
        val cardView = itemView.findViewById(R.id.category_list_row_item) as CardView

    }
}
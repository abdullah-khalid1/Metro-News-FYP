package com.mynews.metronews.ui.profile.admin

import android.app.Activity
import android.app.AlertDialog
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
import com.example.awesomedialog.AwesomeDialog
import com.example.awesomedialog.body
import com.example.awesomedialog.onPositive
import com.example.awesomedialog.title
import com.google.firebase.database.DatabaseReference
import com.mynews.metronews.CheckInternet
import com.mynews.metronews.R
import com.mynews.metronews.model.Video
import com.mynews.metronews.ui.NewsVideoPlayer

class AuthorVideoListAdapter(
    private val context: Context,
    private val arrayList: ArrayList<Video>,
    private val mVideoDbReference: DatabaseReference,
    private val activity: Activity
) :
    RecyclerView.Adapter<AuthorVideoListAdapter.MyHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyHolder {
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

        holder.cardview.setOnLongClickListener {

            alertDialog(arrayList[position].id, position)

            true
        }
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnail = itemView.findViewById(R.id.thumbnail_for_video) as ImageView
        val title = itemView.findViewById(R.id.title_for_video) as TextView
        val cardview = itemView.findViewById(R.id.video_row_item) as CardView
    }


    private fun alertDialog(id: String, position: Int) {
        val alertDialog =
            AlertDialog.Builder(context).setTitle("Action Needed")

                // Delete Button On Alert Dialog
                .setNegativeButton("Delete") { _, _ ->


                    if (CheckInternet(context.applicationContext).isNetworkOnline1()) {
                        mVideoDbReference.child(id).removeValue().addOnSuccessListener {
                            AwesomeDialog.build(activity)
                                .title("Successfully Deleted")
                                .onPositive("OK") {
                                    Log.d("TAG", "positive ")
                                }
                        }.addOnFailureListener {
                            AwesomeDialog.build(activity)
                                .title("Error Occur")
                                .body(it.localizedMessage)
                                .onPositive("OK") {
                                    Log.d("TAG", "positive ")
                                }
                        }
                    } else {
                        AwesomeDialog.build(activity)
                            .title("Internet Isn't Available")
                            .onPositive("OK") {
                                Log.d("TAG", "positive ")
                            }
                    }

                }

                .setNeutralButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }

        alertDialog.setCancelable(false)
        alertDialog.create()
        alertDialog.show()
    }
}
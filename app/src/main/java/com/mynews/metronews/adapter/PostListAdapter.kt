package com.mynews.metronews.adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.awesomedialog.AwesomeDialog
import com.example.awesomedialog.icon
import com.example.awesomedialog.onPositive
import com.example.awesomedialog.title
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mynews.metronews.CheckInternet
import com.mynews.metronews.R
import com.mynews.metronews.model.Post


class PostListAdapter(
    private val context: Context,
    private val list: ArrayList<Post>,
    private val dbrefernce: DatabaseReference,
    private val activity: Activity
) :
    RecyclerView.Adapter<PostListAdapter.MyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {

        val view =
            LayoutInflater.from(context).inflate(R.layout.row_author_post_list, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        holder.title.text = list[position].title
        holder.description.text = list[position].description
        Glide.with(context).load(list[position].downloadUrlImage).centerCrop()
            .into(holder.image)


        holder.title.setOnClickListener {

        }

        holder.linearLayout.setOnLongClickListener {

            alertDialog(list[position].id, position)

            true
        }

    }


    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image: ImageView = itemView.findViewById(R.id.img_author_post_list)
        val title: TextView = itemView.findViewById(R.id.txt_author_post_title)
        val description: TextView = itemView.findViewById(R.id.txt_author_post_des)
        val linearLayout: LinearLayout = itemView.findViewById(R.id.row_author_posts_list)
    }

    private fun alertDialog(id: String, position: Int) {
        val alertDialog =
            AlertDialog.Builder(context).setTitle("Action Needed")
                .setPositiveButton("Update") { dialog, which ->


                    showUpdateDialog(
                        list[position].title,
                        list[position].description,
                        list[position].id,
                        list[position].downloadUrlImage,
                        list[position].email,
                        list[position].date,
                        list[position].author_name
                    )
                }


                // Delete Button On Alert Dialog
                .setNegativeButton("Delete") { dialog, which ->

                    if (CheckInternet(context.applicationContext).isNetworkOnline1()) {
                        dbrefernce.child(id).removeValue()
                    } else {

                        AwesomeDialog.build(activity)
                            .title("Internet Not Available")
                            .icon(R.drawable.ic_error_24)
                            .onPositive("OK") {
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

    private fun updateArticle(
        title: String,
        description: String,
        id: String,
        downloadUrl: String,
        email: String,
        date: String,
        authorName: String
    ) {

        val databaseReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Posts").child(id)
        val post = Post(
            title = title,
            description = description,
            downloadUrlImage = downloadUrl,
            email = email,
            date = date,
            author_name = authorName,
            id = id
        )
        databaseReference.setValue(post).addOnSuccessListener {
            AwesomeDialog.build(activity)
                .title("Successfully Updated")
                .icon(R.drawable.ic_error_24)
                .onPositive("OK") {
                }
        }

    }

    private fun showUpdateDialog(
        titleTxt: String,
        descriptionTxt: String,
        id: String,
        downloadUrl: String,
        email: String,
        date: String,
        authorName: String
    ) {

        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val dialogView: View =
            layoutInflater.inflate(R.layout.update_article_layout, null)

        builder.setView(dialogView)

        // Initializing Widgets

        val title = dialogView.findViewById(R.id.update_title) as EditText
        val description: EditText = dialogView.findViewById(R.id.update_desc)
        val submitUpdate: Button = dialogView.findViewById(R.id.update_submit)

        // Widgets Listners
        title.setText(titleTxt)
        description.setText(descriptionTxt)

        submitUpdate.setOnClickListener {

            if (CheckInternet(context.applicationContext).isNetworkOnline1()) {
                updateArticle(
                    title.text.toString(),
                    description.text.toString(),
                    id,
                    downloadUrl,
                    email,
                    date,
                    authorName
                )
            } else {

                AwesomeDialog.build(activity)
                    .title("Internet Not Available")
                    .icon(R.drawable.ic_error_24)
                    .onPositive("OK") {
                    }
            }
        }


        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

}
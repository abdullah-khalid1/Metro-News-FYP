package com.mynews.metronews.ui.profile.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.awesomedialog.AwesomeDialog
import com.example.awesomedialog.icon
import com.example.awesomedialog.onPositive
import com.example.awesomedialog.title
import com.google.firebase.database.*
import com.mynews.metronews.CheckInternet
import com.mynews.metronews.R
import com.mynews.metronews.model.Video

class AllAuthorsVideos : AppCompatActivity() {


    private val database = FirebaseDatabase.getInstance()
    private var myVideoRefernce: DatabaseReference? = null
    private var videoList: ArrayList<Video>? = null
    private var recyclerView: RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_authors_videos)


        fun init2(){
            if (CheckInternet(this.applicationContext).isNetworkOnline1()) {
                init()
                mFetchingVideo()
            } else {
                AwesomeDialog.build(this)
                    .title("Internet Not Available")
                    .icon(R.drawable.ic_error_24)
                    .onPositive("Retry") {
                        init2()
                    }
            }
        }

        init2()
    }


    private fun init() {
        videoList = ArrayList()
        myVideoRefernce = database.getReference("videos")

        recyclerView = findViewById(R.id.mAllAuthorsVideoList)

        recyclerView!!.layoutManager = LinearLayoutManager(this)
        recyclerView!!.setHasFixedSize(true)
    }


    private fun mFetchingVideo() {
        // Read from the database

        myVideoRefernce!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                videoList!!.clear()

                if (dataSnapshot.exists())


                    for (postsnapshot: DataSnapshot in dataSnapshot.children) {
                        val post: Video = postsnapshot.getValue(Video::class.java)!!
                        videoList!!.add(post)
                    }

                recyclerView!!.adapter =
                    AuthorVideoListAdapter(
                        this@AllAuthorsVideos,
                        videoList!!,
                        myVideoRefernce!!,
                        this@AllAuthorsVideos
                    )

            }
        })
    }
}
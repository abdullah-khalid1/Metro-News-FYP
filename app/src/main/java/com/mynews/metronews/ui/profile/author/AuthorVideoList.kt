package com.mynews.metronews.ui.profile.author

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.awesomedialog.*
import com.google.firebase.database.*
import com.mynews.metronews.CheckInternet
import com.mynews.metronews.R
import com.mynews.metronews.ui.profile.admin.AuthorVideoListAdapter
import com.mynews.metronews.model.Video
import kotlinx.android.synthetic.main.activity_author_video_list.*

class AuthorVideoList : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance()
    private var myVideoRefernce: DatabaseReference? = null
    private var videoList: ArrayList<Video>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_author_video_list)

        val bundle = intent.extras!!
        val mBundleEmail = bundle.getString("email")

        fun init2(){
            if (CheckInternet(this.applicationContext).isNetworkOnline1()) {
                init()
                mFetchingVideo(mBundleEmail!!)
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

        mAuthorVideoList.layoutManager = LinearLayoutManager(this)
        mAuthorVideoList.setHasFixedSize(true)
    }


    private fun mFetchingVideo(email: String) {
        // Read from the database

        myVideoRefernce!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                AwesomeDialog.build(this@AuthorVideoList)
                    .title("Error Occur")
                    .body(p0.message)
                    .icon(R.drawable.ic_error_24)
                    .onPositive("Try Again") {
                    }
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                videoList!!.clear()

                if (dataSnapshot.exists()) {

                    for (postsnapshot: DataSnapshot in dataSnapshot.children) {
                        val post: Video = postsnapshot.getValue(Video::class.java)!!
                        if (post.email == email) {
                            videoList!!.add(post)
                        }
                    }
                }

                if (videoList!!.isNullOrEmpty()){
                    AwesomeDialog.build(this@AuthorVideoList)
                        .title("No Video Available")

                        .onPositive("OK") {
                        }
                }

                mAuthorVideoList!!.adapter =
                    AuthorVideoListAdapter(this@AuthorVideoList, videoList!!, myVideoRefernce!!,this@AuthorVideoList)

            }
        })
    }

}
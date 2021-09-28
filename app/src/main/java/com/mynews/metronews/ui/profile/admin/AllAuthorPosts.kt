package com.mynews.metronews.ui.profile.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.awesomedialog.*
import com.google.firebase.database.*
import com.mynews.metronews.CheckInternet
import com.mynews.metronews.R
import com.mynews.metronews.model.Post
import kotlinx.android.synthetic.main.fragment_author_posts_list.*

class AllAuthorPosts : AppCompatActivity() {

    private var database: FirebaseDatabase? = null
    private var myPostRefernce: DatabaseReference? = null
    private var postList: ArrayList<Post>? = null
    private var recyclerView: RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_author_posts)


        fun init2(){
            if (CheckInternet(this.applicationContext).isNetworkOnline1()) {
                init()
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
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }


    private fun init() {
        database = FirebaseDatabase.getInstance()
        recyclerView = findViewById(R.id.all_authors_posts)
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        recyclerView!!.setHasFixedSize(true)
        myPostRefernce = database!!.getReference("Posts")
        postList = ArrayList()
        fetchingAuthorPost()
    }


    /*
*
* Fetching Post of specific email from FireBase DataBase
*
*/

    private fun fetchingAuthorPost() {
        // Read from the database

        myPostRefernce!!
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    AwesomeDialog.build(this@AllAuthorPosts)
                        .title("Error Occur")
                        .body(p0.message)
                        .icon(R.drawable.ic_error_24)

                        .onPositive("Try Again") {

                        }
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    postList!!.clear()

                    if (dataSnapshot.exists())

                        progressbar.visibility = View.GONE

                    for (postsnapshot: DataSnapshot in dataSnapshot.children) {
                        val post: Post = postsnapshot.getValue(
                            Post::class.java
                        )!!

                        postList!!.add(post)

                    }

                    /*
                    * Checking if there's no post available
                    *
                    *  */
                    if (postList!!.isNullOrEmpty()) {
                        Toast.makeText(
                            applicationContext,
                            "You've No Post Yet !",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }

                    recyclerView!!.adapter =
                        AllAuthorsPostListAdapter(
                           this@AllAuthorPosts,
                            postList!!,
                            myPostRefernce!!,
                            this@AllAuthorPosts
                        )
                }
            })
    }
}
package com.mynews.metronews.ui.profile.author

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.awesomedialog.*
import com.google.firebase.database.*
import com.mynews.metronews.CheckInternet
import com.mynews.metronews.R
import com.mynews.metronews.adapter.PostListAdapter
import com.mynews.metronews.model.Post
import kotlinx.android.synthetic.main.fragment_author_posts_list.*

class AuthorPostsListActivity : AppCompatActivity() {

    private var database: FirebaseDatabase? = null
    private var myPostRefernce: DatabaseReference? = null
    private var postList: ArrayList<Post>? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_author_posts_list)


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
    }

    private fun init() {
        val bundle = intent.extras!!
        val mBundleEmail = bundle.getString("email")
        database = FirebaseDatabase.getInstance()
        recyclerView = findViewById(R.id.author_posts_list)
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        recyclerView!!.setHasFixedSize(true)
        myPostRefernce = database!!.getReference("Posts")
        postList = ArrayList()
        fetchingAuthorPost(mBundleEmail.toString())
    }


    /*
*
* Fetching Post of specific email from FireBase DataBase
*
*/

    private fun fetchingAuthorPost(authorEmail: String) {
        // Read from the database

        myPostRefernce!!
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    AwesomeDialog.build(this@AuthorPostsListActivity)
                        .title("Error Occur")
                        .body(p0.message)

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

                        if (post.email == authorEmail) {
                            postList!!.add(post)
                        }
                    }


                    /*
                    * Checking if there's no post available
                    *
                    *  */
                    if (postList!!.isNullOrEmpty()) {
                        AwesomeDialog.build(this@AuthorPostsListActivity)
                            .title("You haven't posted Yet")

                            .onPositive("OK") {

                            }

                    }

                    recyclerView!!.adapter =
                        PostListAdapter(
                            this@AuthorPostsListActivity,
                            postList!!,
                            myPostRefernce!!,
                            this@AuthorPostsListActivity
                        )
                }
            }
            )
    }
}

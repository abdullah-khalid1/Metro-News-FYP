package com.mynews.metronews.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.awesomedialog.AwesomeDialog
import com.example.awesomedialog.icon
import com.example.awesomedialog.onPositive
import com.example.awesomedialog.title
import com.google.firebase.database.*
import com.mynews.metronews.CheckInternet
import com.mynews.metronews.R
import com.mynews.metronews.adapter.CategoryListAdapter
import com.mynews.metronews.adapter.HomeAdapter
import com.mynews.metronews.model.Category
import com.mynews.metronews.model.Post
import kotlinx.android.synthetic.main.fragment_home.*


class Home : Fragment() {

    private val database = FirebaseDatabase.getInstance("https://metro-news-809b4-default-rtdb.firebaseio.com/")
    private var myPostRefernce: DatabaseReference? = null
    private var postList: ArrayList<Post>? = null
    private var arrayList: ArrayList<Category>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        if (CheckInternet(requireActivity().applicationContext).isConnected()) {
//
//        } else {
//            Toast.makeText(requireActivity().applicationContext, "Not connected", Toast.LENGTH_LONG)
//                .show()
//        }


        fun init2() {
            if (CheckInternet(requireActivity().applicationContext).isNetworkOnline1()) {
                postList = ArrayList()
                myPostRefernce = database.getReference("Posts")
                home_recycler_view.layoutManager = LinearLayoutManager(this@Home.context)

                fetchingUserpost()
            } else {
                AwesomeDialog.build(requireActivity())
                    .title("Internet Not Available")
                    .icon(R.drawable.ic_error_24)
                    .onPositive("Retry") {
                        init2()
                    }
            }


        }

        init2()
    }


    private fun fetchingUserpost() {
        // Read from the database


        myPostRefernce!!.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(requireContext(), p0.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                postList!!.clear()

                if (dataSnapshot.exists()) {

                    if (context != null && activity != null && this@Home.isVisible) {

                        loading_animation_view.visibility = View.GONE
                        home_categegory_txtView.visibility = View.VISIBLE
                        home_pakistan_news_txtView.visibility = View.VISIBLE

                        fetchCategoryList()

                        for (postsnapshot: DataSnapshot in dataSnapshot.children) {
                            val post: Post = postsnapshot.getValue(Post::class.java)!!
                            postList!!.add(post)
                        }

                        postList!!.reverse()
                        home_recycler_view!!.adapter =
                            HomeAdapter(requireContext(), postList!!)
                    }
                }


            }
        })
    }

    private fun fetchCategoryList() {
        arrayList = ArrayList()
        arrayList!!.add(Category("Business", R.drawable.business))
        arrayList!!.add(Category("Technology", R.drawable.technology))
        arrayList!!.add(Category("Health", R.drawable.health))
        arrayList!!.add(Category("Sports", R.drawable.sports))
        arrayList!!.add(Category("Science", R.drawable.science))

        home_category_recycler_view.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        home_category_recycler_view.setHasFixedSize(true)
        home_category_recycler_view.adapter = CategoryListAdapter(requireContext(), arrayList!!)
    }

}
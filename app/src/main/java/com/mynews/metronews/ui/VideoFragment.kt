package com.mynews.metronews.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.awesomedialog.AwesomeDialog
import com.example.awesomedialog.icon
import com.example.awesomedialog.onPositive
import com.example.awesomedialog.title
import com.google.firebase.database.*
import com.mynews.metronews.CheckInternet
import com.mynews.metronews.R
import com.mynews.metronews.adapter.VideoAdapter
import com.mynews.metronews.model.Video

import kotlinx.android.synthetic.main.fragment_video.*

/**
 * A simple [Fragment] subclass.
 */
class VideoFragment : Fragment() {


    private val database = FirebaseDatabase.getInstance()
    private var myVideoRefernce: DatabaseReference? = null
    private var videoList: ArrayList<Video>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Initialization
        init2()
    }


    fun init2() {
        if (CheckInternet(requireActivity().applicationContext).isNetworkOnline1()) {
            init()
            FetchingVideo()
        } else {
            AwesomeDialog.build(requireActivity())
                .title("Internet Not Available")
                .icon(R.drawable.ic_error_24)
                .onPositive("Retry") {
                    init2()
                }
        }
    }

    private fun init() {
        videoList = ArrayList()
        myVideoRefernce = database.getReference("videos")

        video_recycler_view.layoutManager = LinearLayoutManager(this@VideoFragment.context)
        video_recycler_view.setHasFixedSize(true)
    }


    private fun FetchingVideo() {
        // Read from the database

        myVideoRefernce!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                videoList!!.clear()

                if (dataSnapshot.exists()) {
                    if (context != null && activity != null && this@VideoFragment.isVisible) {

                        loading_animation_view.visibility = View.INVISIBLE

                        for (postsnapshot: DataSnapshot in dataSnapshot.children) {
                            val post: Video = postsnapshot.getValue(Video::class.java)!!
                            videoList!!.add(post)
                        }
                        videoList!!.reverse()

                        video_recycler_view!!.adapter =
                            VideoAdapter(this@VideoFragment.requireContext(), videoList!!)
                    }
                }
            }
        })
    }
}

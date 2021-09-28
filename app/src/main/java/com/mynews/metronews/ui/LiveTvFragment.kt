package com.mynews.metronews.ui


import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.mynews.metronews.R
import com.mynews.metronews.adapter.TvListAdapter
import com.mynews.metronews.model.Tv
import kotlinx.android.synthetic.main.fragment_live_tv.*

/**
 * A simple [Fragment] subclass.
 */
class LiveTvFragment : Fragment() {

    private var tvThumbnailList: ArrayList<Tv>? = null
    private var player: SimpleExoPlayer? = null
    private var playerView: PlayerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live_tv, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
        init()

    }

    private fun init() {

        player = SimpleExoPlayer.Builder(requireContext().applicationContext).build()
        playerView = requireActivity().findViewById(R.id.live_tv_exo_player)
        tvThumbnailList = ArrayList()

        tvThumbnailList.apply {

            this!!.add(Tv(R.drawable.geo))
            add(Tv(R.drawable.ary))
            add(Tv(R.drawable.samaa))
            add(Tv(R.drawable.gnn))


        }


        playerView!!.player = player

        recycler_view_for_live_tv.layoutManager = GridLayoutManager(context, 4)
        recycler_view_for_live_tv.setHasFixedSize(true)
        recycler_view_for_live_tv.adapter =
            TvListAdapter(tvThumbnailList!!, requireContext().applicationContext, player!!)
    }


    fun videoViewVisibility(visibility: Boolean) {
        if (visibility && live_tv_exo_player != null) {
            live_tv_exo_player.visibility = View.VISIBLE
        }
    }


    override fun onPause() {
        super.onPause()
        if (player != null) {
            pausePlayer()
        }
    }

    override fun onResume() {
        super.onResume()

        startPlayer()
    }

    private fun pausePlayer() {
        player!!.playWhenReady = false
        player!!.playbackState
    }

    private fun startPlayer() {
        player!!.playWhenReady = true
        player!!.playbackState
    }
}

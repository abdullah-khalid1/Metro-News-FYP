package com.mynews.metronews.ui


import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mynews.metronews.R
import kotlinx.android.synthetic.main.fragment_news_detail.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class NewsDetail : Fragment() {

    private var tts: TextToSpeech? = null
    private var title: TextView? = null
    private var description: TextView? = null
    private var imageView: ImageView? = null
    private var speakButton: Button? = null

    private var speakArticle: LinearLayout? = null
    private var pauseArticle: LinearLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title = requireActivity().findViewById(R.id.news_title_id)
        description = requireActivity().findViewById(R.id.news_description_id)
        imageView = requireActivity().findViewById(R.id.news_image_id)
        speakButton = requireActivity().findViewById(R.id.speak)

        speakArticle = requireActivity().findViewById(R.id.play_linear_layout)
        pauseArticle = requireActivity().findViewById(R.id.pause_linear_layout)


        val descriptionArg = arguments?.getString("news_description")
        val titleArg = arguments?.getString("news_title")
        val imageUrlArg = arguments?.getString("news_image_url")
        val date = arguments?.getString("news_date")



        title!!.text = titleArg
        description!!.text = descriptionArg
        news_date.text = date

        Glide.with(this).load(imageUrlArg).centerCrop()
            .into(imageView!!)



        speakArticle!!.setOnClickListener {

            tts!!.speak(descriptionArg.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
            speakArticle!!.visibility = View.GONE
            pauseArticle!!.visibility = View.VISIBLE

        }

        pauseArticle!!.setOnClickListener {
            if (tts!!.isSpeaking) {
                tts!!.stop()
                pauseArticle!!.visibility = View.GONE
                speakArticle!!.visibility = View.VISIBLE
            }
        }
    }

    override fun onPause() {
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
            speakArticle!!.visibility = View.VISIBLE
            pauseArticle!!.visibility = View.GONE
        }
        super.onPause()
    }

    override fun onResume() {
        super.onResume()

        tts = TextToSpeech(context) {
            if (it != TextToSpeech.ERROR) {
                tts!!.language = Locale.ENGLISH
            }
        }
    }
}
package com.mynews.metronews.ui.profile.author

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.awesomedialog.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.mynews.metronews.CheckInternet
import com.mynews.metronews.R
import com.mynews.metronews.model.Video
import com.mynews.metronews.util.Constant
import kotlinx.android.synthetic.main.activity_insert_video.*
import java.util.*


class InsertVideoActivity : AppCompatActivity() {

    private var mVideoUploadButton: Button? = null
    private var mSubmitVideoButton: Button? = null


    private val database = FirebaseDatabase.getInstance()
    private var mVideoRefernce: DatabaseReference? = null

    //Firebase Storage
    private var storage: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private var mVideoUri: Uri? = null
    private var downloadUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_video)


        // Initialization
        initWidget()
        init()

        mVideoUploadButton!!.setOnClickListener {
            videoChooserFromGallery()
        }
        mSubmitVideoButton!!.setOnClickListener {

            if (CheckInternet(this.applicationContext).isNetworkOnline1()) {

                uploadVideo()

            } else {
                AwesomeDialog.build(this)
                    .title("Internet Not Available")
                    .icon(R.drawable.ic_error_24)
                    .onPositive("OK") {
                    }
            }

        }
    }

    private fun initWidget() {
        mVideoUploadButton = findViewById(R.id.m_video_upload)
        mSubmitVideoButton = findViewById(R.id.submit_video)
    }

    private fun init() {
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference
        mVideoRefernce = database.getReference("videos")
    }

    private fun videoChooserFromGallery() {
        val intent = Intent()
        intent.type = "video/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, Constant.IMAGE_CHOOSER_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constant.IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            mVideoUri = data.data
            //Glide.with(this).load(mImageUri).into(imageView)
        }
    }

    // Upload Video
    private fun uploadVideo() {

        if (mVideoUri != null) {
            val ref = storageReference!!.child("videos/" + UUID.randomUUID())

            ref.putFile(mVideoUri!!)

                .addOnSuccessListener {
                    downloadUrl = it.metadata!!.reference!!.downloadUrl.toString()

                    // Post().downloadUrlImage = downloadUrl!!
                    Log.d("downloadUrl", ref.downloadUrl.toString())
                    ref.downloadUrl.addOnSuccessListener {
                        val uri: Uri = it
                        //    Toast.makeText(this, "url $uri", Toast.LENGTH_LONG).show()
                        /*
                        ** Insert Into DataBase
                         */

                        if (m_video_title.text != null) {

                            val bundle = intent.extras!!
                            val mBundleEmail = bundle.getString("email")

                            insertVideoDataBase(
                                m_video_title.text.toString(),
                                mBundleEmail.toString(),
                                uri.toString()
                            )
                        }

                    }.addOnFailureListener {
                        AwesomeDialog.build(this)
                            .title("Error Occur")
                            .body(it.localizedMessage)
                            .onPositive("Try Again") {
                            }
                    }
                }
                .addOnProgressListener {

                    val progress: Double =
                        (100 * it.bytesTransferred / it.totalByteCount).toDouble()
                    progressBar.progress = progress.toInt()

                    progress_txtView.text = progress.toInt().toString() + "%"
                    if (progress.toInt() > 1) {
                        progress_bar_linear.visibility = View.VISIBLE
                    }

                    if (progress.toInt() == 100) {
                        AwesomeDialog.build(this)
                            .title("Sucessfully Uploaded")
                            .icon(R.drawable.ic_baseline_cloud_done)
                            .onPositive("OK") {
                            }
                    }
                }
        } else if (mVideoUri == null) {
            AwesomeDialog.build(this)
                .title("Please Select Video")
                .icon(R.drawable.ic_baseline_cloud_done)
                .onPositive("OK") {
                }
        }
    }

    // Insert Into DataBase
    private fun insertVideoDataBase(title: String, email: String, downloadUrl: String) {
        // Adding New Post
        val id: String = mVideoRefernce!!.push().key!!
        val video = Video(email, title, downloadUrl, id = id)
        mVideoRefernce!!.child(id).setValue(video)
    }
}
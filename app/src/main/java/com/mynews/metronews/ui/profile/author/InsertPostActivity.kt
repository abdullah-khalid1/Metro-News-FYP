package com.mynews.metronews.ui.profile.author

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.awesomedialog.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.mynews.metronews.CheckInternet
import com.mynews.metronews.R
import com.mynews.metronews.model.Post
import com.mynews.metronews.util.Constant
import kotlinx.android.synthetic.main.activity_insert_post.*


import java.text.SimpleDateFormat
import java.util.*

class InsertPostActivity : AppCompatActivity() {

    private var mInsertTitle: EditText? = null
    private var mInsertParagraph: EditText? = null
    private var mImageChooser: ImageView? = null
    private var mSubmitPost: Button? = null

    private var calendar: Calendar? = null
    private var simpledateformat: SimpleDateFormat? = null
    private var date: String? = null
    private val database = FirebaseDatabase.getInstance()
    private var myPostRefernce: DatabaseReference? = null
    private var mImageUri: Uri? = null

    //Firebase Storage
    private var storage: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private var downloadUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_post)

        initWidgets()
        init()
    }

    private fun initWidgets() {
        mImageChooser = findViewById(R.id.imageView)
        mSubmitPost = findViewById(R.id.submit_post)
        mInsertParagraph = findViewById(R.id.paragraph)
        mInsertTitle = findViewById(R.id.title)
    }


    private fun init() {
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference

        myPostRefernce = database.getReference("Posts")

        calendar = Calendar.getInstance()
        simpledateformat = SimpleDateFormat("dd-MM-yyyy")
        date = simpledateformat!!.format(calendar!!.time)

        Toast.makeText(this, "" + date, Toast.LENGTH_LONG).show()

        Glide.with(this).load(R.drawable.ic_baseline_attachment_24).into(imageView)

        mSubmitPost!!.setOnClickListener {
            if (CheckInternet(this.applicationContext).isNetworkOnline1()) {
                uploadImage()
            } else {
                AwesomeDialog.build(this)
                    .title("Internet Not Available")
                    .icon(R.drawable.ic_error_24)
                    .onPositive("OK") {
                    }
            }

        }

        mImageChooser!!.setOnClickListener {
            imageChooserFromGallery()
        }
    }

    private fun insertIntoDataBase(
        title: String,
        paragraph: String,
        email: String,
        downloadUrl: String
    ) {
        // Adding New Post
        val id: String = myPostRefernce!!.push().key!!
        val post = Post(
            email,
            title,
            paragraph,
            "Imran",
            id = id,
            downloadUrlImage = downloadUrl,
            date = date!!
        )
        myPostRefernce!!.child(id).setValue(post)


    }


    private fun imageChooserFromGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, Constant.IMAGE_CHOOSER_REQUEST_CODE)
    }

    private fun uploadImage() {

        if (mImageUri != null) {

            Log.d("Message", "Before ref")
            val ref = storageReference!!.child("images/" + UUID.randomUUID())
            Log.d("Message", "Before ref")

            ref.putFile(mImageUri!!)
                .addOnSuccessListener {
                    downloadUrl = it.metadata!!.reference!!.downloadUrl.toString()

                    // Post().downloadUrlImage = downloadUrl!!
                    Log.d("downloadUrl", ref.downloadUrl.toString())
                    ref.downloadUrl.addOnSuccessListener {
                        val uri: Uri = it
                        Toast.makeText(this, "url $uri", Toast.LENGTH_LONG).show()

                        if (mInsertTitle!!.text != null && mInsertParagraph!!.text != null) {

                            val bundle = intent.extras!!
                            val mBundleEmail = bundle.getString("email")
                            insertIntoDataBase(
                                mInsertTitle!!.text.toString(),
                                paragraph.text.toString(),
                                mBundleEmail.toString(),
                                uri.toString()
                            )
                        }
                    }
                }

                .addOnProgressListener {
                    val progress : Double= (100 * it.bytesTransferred / it.totalByteCount).toDouble()
                    progressBar.progress = progress.toInt()

                    if (progress.toInt() == 100){
                        AwesomeDialog.build(this)
                            .title("Published")
                            .icon(R.drawable.ic_baseline_cloud_done)
                            .onPositive("OK") {

                            }
                    }
                }

                .addOnFailureListener {
                    AwesomeDialog.build(this)
                        .title("Error Occur")
                        .body(it.localizedMessage)

                        .onPositive("Try Again") {

                        }

                }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constant.IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            mImageUri = data.data
            Glide.with(this).load(mImageUri).into(imageView)
        }
        else
            if (resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(this,"error",Toast.LENGTH_LONG).show()
            }
    }
}

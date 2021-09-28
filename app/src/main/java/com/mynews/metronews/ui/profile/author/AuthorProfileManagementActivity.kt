package com.mynews.metronews.ui.profile.author

import `in`.shrinathbhosale.preffy.Preffy
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.mynews.metronews.R
import com.mynews.metronews.ui.MainActivity
import kotlinx.android.synthetic.main.activity_profile_management.*

class AuthorProfileManagementActivity : AppCompatActivity() {

    var preffy: Preffy? = null
    private var mEditButton: Button? = null
    private var mAuthorVideos: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_management)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        initWidgets()
        init()


    }


    private fun init() {
        preffy = Preffy.getInstance(this)

        val bundle = intent.extras!!
        val mBundleEmail = bundle.getString("email")


        floatingActioncancel.setOnClickListener { dialogForAccount() }


//        insertpost.setOnClickListener {
////
////            it!!.findNavController()
////                .navigate(R.id.insertPostFragment, emailbundle)
//
//            val intent: Intent = Intent(this, InsertPostActivity::class.java)
//            intent.putExtra("email", mBundleEmail)
//            startActivity(intent)
//        }

//        logout.setOnClickListener {
//
//            editor = this.getSharedPreferences(
//                Constant.PREFERENCES_FILE_NAME,
//                Context.MODE_PRIVATE
//            ).edit()
//
//            editor!!.remove(Constant.PREFERENCES_PASS)
//            editor!!.remove(Constant.PREFERENCES_EMAIL)
//            editor!!.commit()
//            // it!!.findNavController().navigate(R.id.Profile)
//        }

//        add_video_id.setOnClickListener {
//            val intent = Intent(this, InsertVideoActivity::class.java)
//            intent.putExtra("email", mBundleEmail)
//            startActivity(intent)
//        }

        mEditButton!!.setOnClickListener {
            val intent = Intent(this, AuthorPostsListActivity::class.java)
            intent.putExtra("email", mBundleEmail)
            startActivity(intent)
        }

        mAuthorVideos!!.setOnClickListener {

            val intent = Intent(this, AuthorVideoList::class.java)
            intent.putExtra("email", mBundleEmail)
            startActivity(intent)
        }

        floatingActionButton.setOnClickListener {
            showDialog(mBundleEmail!!)
        }
    }

    private fun initWidgets() {
        mEditButton = findViewById(R.id.mEditPosts)
        mAuthorVideos = findViewById(R.id.mAAuthorVideos)
    }


    private fun showDialog(mBundleEmail: String) {


        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Choose An Option")

        builder.setNeutralButton("Cancel") { l, i ->
            l.dismiss()
        }

        builder.setPositiveButton("Add Article") { l, i ->

            val intent: Intent = Intent(this, InsertPostActivity::class.java)
            intent.putExtra("email", mBundleEmail)
            startActivity(intent)
        }

        builder.setNegativeButton("Add Video") { l, o ->
            val intent = Intent(this, InsertVideoActivity::class.java)
            intent.putExtra("email", mBundleEmail)
            startActivity(intent)
        }

        builder.setCancelable(false)
        builder.show()
    }

    private fun dialogForAccount() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Choose An Option")

        builder.setNeutralButton("Cancel") { l, i ->
            l.dismiss()
        }

        builder.setPositiveButton("LogOut") { i, l ->


            preffy!!.removeAll()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        builder.setCancelable(false)
        builder.show()
    }

    override fun onBackPressed() {
        val  intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        this.finish()
        super.onBackPressed()
    }
}

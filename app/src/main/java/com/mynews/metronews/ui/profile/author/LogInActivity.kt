package com.mynews.metronews.ui.profile.author

import `in`.shrinathbhosale.preffy.Preffy
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.awesomedialog.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.mynews.metronews.R
import com.mynews.metronews.ui.MainActivity
import com.mynews.metronews.ui.profile.admin.AdminProfileManagement
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlinx.android.synthetic.main.fragment_profile.profile_email
import kotlinx.android.synthetic.main.fragment_profile.profile_password
import kotlinx.android.synthetic.main.fragment_profile.signIn

class LogInActivity : AppCompatActivity() {

    var auth: FirebaseAuth? = null
    // var progressBar: LottieAnimationView? = null

    var preffy: Preffy? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }



        init()
    }

    private fun init() {
//        progressBar = findViewById(R.id.loading_animation_view);
//        progressBar!!.visibility = View.INVISIBLE; // To show the ProgressBar
        preffy = Preffy.getInstance(this)
        val email = preffy!!.getString("email")
        val password = preffy!!.getString("password")
        val exist = preffy!!.getBoolean("exist")

        if (exist) {
            signIn(
                email, password
            )
        }


        signIn.setOnClickListener {
            //   progressBar!!.visibility = View.VISIBLE;
            signIn(profile_email.text.toString(), profile_password.text.toString())
        }

        admin_SignIn.setOnClickListener {
            if (profile_email.text.toString() == "admin@metronews.com" && profile_password.text.toString() == "123456") {
                val intent = Intent(this, AdminProfileManagement::class.java)
                startActivity(intent)
            }
            else
            {
                AwesomeDialog.build(this)
                    .title("Error")
                    .body("Please Check Your Login Details")
                    .icon(R.drawable.ic_error_24)
                    .onPositive("OK") {
                        Log.d("TAG", "positive ")
                    }
            }
        }
    }


    private fun signIn(email: String, password: String) {
        showLoading()

        auth = FirebaseAuth.getInstance()

        auth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {

                if (it.isSuccessful) {

                    preffy!!.putString("email", email)
                    preffy!!.putString("password", password)
                    preffy!!.putBoolean("exist", true)

                    val firebaseruser: FirebaseUser = auth!!.currentUser!!
                    hideLoading()

                    val intent = Intent(this, AuthorProfileManagementActivity::class.java)
                    intent.putExtra("email", firebaseruser.email)
                    startActivity(intent)

                    this@LogInActivity.finish()
//                    view.findNavController().navigate(R.id.afterAuthorSignIn, emailBundle)
//                    startActivity()
                }

            }.addOnFailureListener {
                when (it) {
                    is FirebaseAuthInvalidCredentialsException -> {
                        hideLoading()

                        AwesomeDialog.build(this)
                            .title("Invalid Password")
                            .icon(R.drawable.ic_error_24)
                            .onPositive("Try Again") {
                                Log.d("TAG", "positive ")
                            }
                    }
                    is FirebaseAuthInvalidUserException -> {
                        hideLoading()
                        AwesomeDialog.build(this)
                            .title("Author Not Exist")
                            .icon(R.drawable.ic_error_24)
                            .onPositive("OK") {
                                Log.d("TAG", "positive ")
                            }
                    }
                    else -> {
                        it.localizedMessage
                        hideLoading()
                        AwesomeDialog.build(this)
                            .title("Error Occur")
                            .body(it.localizedMessage)
                            .icon(R.drawable.ic_error_24)

                            .onPositive("Try Again") {
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                this@LogInActivity.finish()
                            }
                            .setCancelable(false)
                    }
                }
            }
    }

    private fun showLoading() {
        rlLoading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        rlLoading.visibility = View.GONE
    }

    override fun onBackPressed() {

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        this@LogInActivity.finish()
        super.onBackPressed()
    }

}
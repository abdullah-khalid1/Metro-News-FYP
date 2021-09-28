package com.mynews.metronews.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.awesomedialog.*
import com.mynews.metronews.NewsApi
import com.mynews.metronews.R
import com.mynews.metronews.adapter.CategoryAdapter
import com.mynews.metronews.model.Article
import com.mynews.metronews.model.ResponseModel
import com.mynews.metronews.util.Constant
import kotlinx.android.synthetic.main.activity_news_api.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsApiActivity : AppCompatActivity() {
    var bundle: Bundle ?= null
    var indexPosition: Int ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_api)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        bundle = intent.extras!!

         indexPosition = bundle!!.getInt("value")

        init(indexPosition!!)

    }

    private fun init(IndexPosition: Int) {

        var call: Call<ResponseModel>? = null

        val retrofit: Retrofit = Retrofit.Builder().baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

        val newsApi: NewsApi = retrofit.create(NewsApi::class.java)

        // val call: Call<ResponseModel> = newsApi.getBussinessNews()

        when (IndexPosition) {

            0 -> {
                call = newsApi.getBussinessNews()
            }
            1 -> {
                call = newsApi.getTechnologyNews()
            }
            2 -> {
                call = newsApi.getHealthNews()
            }

            3 -> {
                call = newsApi.getSportsNews()
            }
            4 -> {
                call = newsApi.getScienceNews()
            }
        }

        call!!.enqueue(object : Callback<ResponseModel> {
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                loading_animation_view.visibility = View.INVISIBLE
                AwesomeDialog.build(this@NewsApiActivity)
                    .title("Error")
                    .icon(R.drawable.ic_error_24)
                    .onPositive("Retry") {
                        init(indexPosition!!)
                    }
            }

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {


                if (response.body() != null) {

                    loading_animation_view.visibility = View.INVISIBLE

                    val articlesList: List<Article> = response.body()!!.articles!!
                    news_list.layoutManager =
                        LinearLayoutManager(this@NewsApiActivity)
                    news_list.setHasFixedSize(true)
                    news_list.adapter =
                        CategoryAdapter(articlesList, this@NewsApiActivity)
                }
            }
        })
    }
}
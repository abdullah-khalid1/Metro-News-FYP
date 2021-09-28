package com.mynews.metronews

import com.mynews.metronews.model.ResponseModel
import com.mynews.metronews.util.Constant
import retrofit2.Call
import retrofit2.http.GET

interface NewsApi {

    @GET("${Constant.BASE_URL}top-headlines?country=us&category=business&apiKey=${Constant.API_KEY}")
    fun getBussinessNews(): Call<ResponseModel>

    @GET("${Constant.BASE_URL}top-headlines?country=us&category=technology&apiKey=${Constant.API_KEY}")
    fun getTechnologyNews(): Call<ResponseModel>

    @GET("${Constant.BASE_URL}top-headlines?country=us&category=health&apiKey=${Constant.API_KEY}")
    fun getHealthNews(): Call<ResponseModel>

    @GET("${Constant.BASE_URL}top-headlines?country=us&category=sports&apiKey=${Constant.API_KEY}")
    fun getSportsNews(): Call<ResponseModel>

    @GET("${Constant.BASE_URL}top-headlines?country=us&category=science&apiKey=${Constant.API_KEY}")
    fun getScienceNews(): Call<ResponseModel>
}
package com.example.mobikodeassignment.network

import com.example.mobikodeassignment.model.ResponseCall
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("search?q=title:DNA")
    fun getBooks(): Call<ResponseCall>
}

package com.example.mobikodeassignment

import android.content.Context
import android.util.Log
import com.example.mobikodeassignment.network.ApiClient
import com.example.mobikodeassignment.network.ApiInterface
import com.example.mobikodeassignment.model.ResponseCall
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GetBookIntractorImpl : BookListContract.getBookIntractor{

    override fun getBookArrayList(onFinishedListener: BookListContract.getBookIntractor.OnFinishedListener?, context: Context) {
        val api = ApiClient().getClient().create(ApiInterface::class.java)
        val call = api.getBooks()

        call.enqueue(object : Callback<ResponseCall> {
            override fun onResponse(call: Call<ResponseCall>, response: Response<ResponseCall>) {

                val resp = response.body() as ResponseCall
                val responseDt = resp.responseDt!!

                Log.e("response==","numFound = ${responseDt.numFound} start = ${responseDt.start} maxScore = ${responseDt.maxScore} docs = ${responseDt.docs}")
                val bookList = responseDt.docs
                onFinishedListener!!.onFinished(bookList!!, context)
            }

            override fun onFailure(call: Call<ResponseCall>, t: Throwable) {
                Log.e("EXCEPTION", "EXCEPTION WHILE GET \n$t")
                onFinishedListener!!.onFailure(t)
            }
        })
    }
}
package com.example.mobikodeassignment

import android.content.Context
import com.example.mobikodeassignment.model.Book

class BookListContract {

    interface mainView{
        fun showProgress()
        fun hideProgress()
        fun setDataToRecyclerView(bookArrayList: List<Book>)
        fun onResponseFailure(throwable: Throwable?)
    }

    interface presenter {
        fun onDestroy()
        fun requestDataFromServer(context: Context)
        fun getDataFromDatabase(context: Context)
    }

    interface getBookIntractor {
        interface OnFinishedListener {
            fun onFinished(bookList: List<Book>, context: Context)
            fun onFailure(t: Throwable?)
        }
        fun getBookArrayList(onFinishedListener: OnFinishedListener?, context: Context)
    }
}
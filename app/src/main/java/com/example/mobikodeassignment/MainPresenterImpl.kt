package com.example.mobikodeassignment

import android.content.Context
import com.example.mobikodeassignment.database.DBHelper
import com.example.mobikodeassignment.model.Book

class MainPresenterImpl constructor(private val mainView:BookListContract.mainView, private val getBookIntractor:BookListContract.getBookIntractor):BookListContract.presenter, BookListContract.getBookIntractor.OnFinishedListener{

    private var mView:BookListContract.mainView ?= null
    private var bookInteractor:BookListContract.getBookIntractor ? =null

    init {
        mView = mainView
        bookInteractor = getBookIntractor
    }

    override fun onDestroy() {
        mView = null
    }

    override fun requestDataFromServer(context: Context) {
        mainView.showProgress()
        getBookIntractor.getBookArrayList(this, context)
    }

    override fun getDataFromDatabase(context: Context) {
      getDataFromDb(context)
    }

    override fun onFinished(bookList: List<Book>, context: Context) {
        val db = DBHelper(context)
        db.upsertBookData(bookList)

        getDataFromDb(context)
        mainView.setDataToRecyclerView(bookList)
        mainView.hideProgress()
    }

    override fun onFailure(t: Throwable?) {
        mainView.onResponseFailure(t)
        mainView.hideProgress()
    }

    fun getDataFromDb(context: Context){
        val db = DBHelper(context)
        val bookList = db.getBookData()
        mainView.setDataToRecyclerView(bookList)
        mainView.hideProgress()
    }
}
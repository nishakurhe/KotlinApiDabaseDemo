package com.example.mobikodeassignment

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobikodeassignment.adapter.BookAdapter
import com.example.mobikodeassignment.database.DBHelper
import com.example.mobikodeassignment.model.Book


class MainActivity : AppCompatActivity(), BookListContract.mainView {

    private var progressBar: ProgressBar? = null
    private var recyclerView: RecyclerView? = null
    private var presenter: BookListContract.presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
        initProgressBar()

        // check data available in db
        val db = DBHelper(this)
        val bookDt = db.getBookData()
        if (bookDt.size>0){
            presenter = MainPresenterImpl(this, GetBookIntractorImpl())
            presenter!!.getDataFromDatabase(this)
        }
        else{
            if (isNetworkReachable()){
                presenter = MainPresenterImpl(this, GetBookIntractorImpl())
                presenter!!.requestDataFromServer(this)
            }
            else Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
        }
    }

    override fun showProgress() {
        progressBar!!.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar!!.visibility = View.INVISIBLE
    }

    override fun setDataToRecyclerView(bookArrayList: List<Book>) {
        val adapter = BookAdapter(this, bookArrayList)
        recyclerView!!.adapter = adapter
    }

    override fun onResponseFailure(throwable: Throwable?) {
        Toast.makeText(this, "Something went wrong...Error", Toast.LENGTH_SHORT).show()
    }

    private fun initRecyclerView(){
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView?.layoutManager = LinearLayoutManager(this)
    }

    private fun initProgressBar(){
        progressBar = findViewById(R.id.progressBar)
        progressBar!!.isIndeterminate = true
        progressBar!!.visibility = View.INVISIBLE
    }

    private fun isNetworkReachable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) ?: return false
        return true
    }
}
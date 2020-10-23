package com.example.mobikodeassignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobikodeassignment.R
import com.example.mobikodeassignment.model.Book
import kotlinx.android.synthetic.main.book_item_layout.view.*

class BookAdapter constructor(private val context: Context, private val bookList:List<Book>) : RecyclerView.Adapter<BookAdapter.MovieViewHolder> () {
    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return  MovieViewHolder(LayoutInflater.from(context).inflate(R.layout.book_item_layout,parent,false))
    }

    override fun getItemCount() = bookList.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.itemView.book_id.text = bookList[position].id
        holder.itemView.publication_dt.text = bookList[position].publicationDate
        holder.itemView.article_type.text = bookList[position].articleType
        var abs = bookList[position].abstract[0]
        // if abstract value is empty so display NA to format UI
        if (abs.isBlank()) abs = "NA"
        else if (!abs.startsWith("\n")) abs = "\n"+abs
        holder.itemView.book_abstract.text = abs
    }
}
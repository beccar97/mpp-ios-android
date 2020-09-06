package com.jetbrains.handson.mpp.mobile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyListAdapter : RecyclerView.Adapter<MyListAdapter.MyViewHolder>() {
    private var books = emptyList<Book>()

    class MyViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        private lateinit var book: Book

        // Setup the view for an individual data item
        fun bindItems(book: Book) {
            this.book = book

            val authorView = view.findViewById<TextView>(R.id.author)
            val titleView = view.findViewById<TextView>(R.id.title)

            authorView.text = book.author
            titleView.text = book.title
        }
    }

    // Create new views - invoked by the layout manager
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

        return MyViewHolder(view)
    }

    // Replace contents of a view - invoked by the layout manager
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItems(books[position])
    }

    // Return the size of the data set - invoked by the layout manager
    override fun getItemCount(): Int {
        return books.size
    }

    fun updateList(books: List<Book>) {
        this.books = books
        // Notify change to data set, so that LayoutManager knows to reload the table
        notifyDataSetChanged()
    }
}

data class Book (
    val author: String,
    val title: String
)
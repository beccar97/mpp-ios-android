package com.jetbrains.handson.mpp.mobile

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), ApplicationContract.View {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: MyListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val presenter = ApplicationPresenter()
        presenter.onViewTaken(this)

        recyclerView = findViewById(R.id.my_recycler_view)

        viewAdapter = MyListAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = viewAdapter

        val books = listOf(
            Book("J.R.R. Tolkien", "The Fellowship of the Ring"),
            Book("Douglas Adams", "The Hitchhiker's Guide to the Galaxy"),
            Book("Hugh Howey", "Wool")
        )

        viewAdapter.updateList(books)
    }

    override fun setLabel(text: String) {
        findViewById<TextView>(R.id.main_text).text = text
    }
}

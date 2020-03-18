package com.example.testproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val linearLayoutManager = LinearLayoutManager(this)
    val itemList = ArrayList<Item>()
    private lateinit var adapter: RecylcerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initial list items
        for (i in 0..20) {
            itemList.add(Item("Item " + i))
        }

        adapter = RecylcerAdapter(itemList, this)
        recyclerview.adapter = adapter
        recyclerview.setHasFixedSize(true)
        recyclerview.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.layoutManager = LinearLayoutManager(this)

        recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!adapter.isLoading()) {
                    Log.v(
                        "endless Scroll",
                        "last visible position : ${linearLayoutManager.findLastCompletelyVisibleItemPosition()}, total count: ${linearLayoutManager.itemCount}"
                    )
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() >= linearLayoutManager.itemCount - 1) {

                        recyclerView.post {
                            adapter.addFooter()
                        }
                        val handler = Handler()
                        handler.postDelayed({
                            adapter.removeFooter()
                            val newItems = ArrayList<Item>()
                            for (i in itemList.size..itemList.size + 19) {
                                newItems.add(Item("Item " + i))
                            }
                            adapter.addItems(newItems)
                        }, 2000)
                    }
                }
            }
        })
    }
}

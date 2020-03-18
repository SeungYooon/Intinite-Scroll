package com.example.testproject

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.progressbar.view.*
import java.lang.Exception
import java.lang.RuntimeException

class RecylcerAdapter(var itemList: ArrayList<Item>, var context:Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // item view
    val REGURAL_ITEM = 0
    // progress bar
    val FOOTER_ITEM = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == REGURAL_ITEM) {
            val view =
                LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
            return RegularViewHolder(view)
        } else if (viewType == FOOTER_ITEM) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.progressbar, parent, false)
            return FooterViewHolder(view)
        } else {
            throw RuntimeException("the types has to be One or Two")
        }
    }

    override fun getItemCount(): Int = itemList.size

    fun addItems(items: ArrayList<Item>) {
        val lastPos = itemList.size - 1
        itemList.addAll(items)
        notifyItemRangeInserted(lastPos, items.size)
    }

    fun addFooter() {
        Log.d("endless Scroll", "add Footer")
        if (!isLoading()) {
            itemList.add(Item("Footer", 1))
            notifyItemInserted(itemList.size - 1)
        }
    }

    fun isLoading(): Boolean {
        return itemList.last().type == FOOTER_ITEM
    }

    fun removeFooter() {
        Log.v("endless Scroll", "remove Footer")
        if (isLoading()) {
            itemList.removeAt(itemList.size - 1)
            notifyItemRemoved(itemList.size - 1)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            REGURAL_ITEM -> {
                holder as RegularViewHolder
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = itemList[position]
        if (item.type == REGURAL_ITEM) {
            return REGURAL_ITEM
        } else if (item.type == FOOTER_ITEM) {
            return FOOTER_ITEM
        }
        throw Exception("Error, unkown view type")
    }

    inner class RegularViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var item: TextView

        init {
            itemView.setOnClickListener(this)
            item = itemView.findViewById(R.id.textview)
        }

        override fun onClick(view: View?) {
            Log.v("On Click", "On Click" + layoutPosition + "" + item.text)
            Toast.makeText(view!!.context, "아이템 클릭" + layoutPosition + "" +item.text, Toast.LENGTH_SHORT).show()
        }
    }

    inner class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var progressBar: ProgressBar = itemView.progressbar
    }
}
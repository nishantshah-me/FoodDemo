package com.example.ecommercedemo.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommercedemo.R

const val TYPE_HEADER = 0
const val TYPE_ITEM = 1

class RecyclerAdapter(private val data: List<String>, private val headerCheck: List<Boolean>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_HEADER) TabsHeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_header, parent, false))
        else TabsItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_recyclerview, parent, false))
    }

    override fun getItemCount(): Int = data.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_ITEM)
            (holder as TabsItemViewHolder).managerName.text = data[position]
        else (holder as TabsHeaderViewHolder).headerText.text = data[position]
    }

    class TabsItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val managerName: TextView = itemView.findViewById(R.id.tv_restrotitle)
    }

    class TabsHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headerText: TextView = itemView.findViewById(R.id.text_header)
    }

    override fun getItemViewType(position: Int): Int =
    if (headerCheck[position]) TYPE_HEADER else TYPE_ITEM
}
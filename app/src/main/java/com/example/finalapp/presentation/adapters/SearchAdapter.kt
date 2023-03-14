package com.example.finalapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalapp.R

class SearchAdapter() : RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemSearchView: TextView = view.findViewById(R.id.ed_search)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recent_recipes, parent, false)
        return MyViewHolder(view)
    }

//    override fun onBindViewHolder(holder: SearchAdapter.MyViewHolder, position: Int) {
//        holder.itemSearchView.text = filteredList[position]
//    }

//    override fun getItemCount(): Int {
//        return filteredList.size
//    }

    fun filter(query: String) {
        filteredList = itemList.filter { it.contains(query, true) }
        notifyDataSetChanged()
    }
}
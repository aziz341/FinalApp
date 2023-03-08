package com.example.finalapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finalapp.R
import com.example.finalapp.databinding.ItemRecentRecipesBinding
import com.example.finalapp.presentation.callback.PostItemDiffCallBack
import com.example.finalapp.presentation.viewHolders.PostViewHolder
import com.example.finalapp.domain.model.Post

class PostAdapter(val listener: Listener) : RecyclerView.Adapter<PostViewHolder>() {
    var postList: List<Post> = mutableListOf()
        set(newValue) {
            val diffCallBack = PostItemDiffCallBack(field, newValue)
            val result = DiffUtil.calculateDiff(diffCallBack)
            field = newValue
            result.dispatchUpdatesTo(this)
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder(
            ItemRecentRecipesBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_recent_recipes, parent, false)
            )
        )


    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(postList[position], listener)
    }

    override fun getItemCount(): Int = postList.size

    interface Listener {
        fun onClick(post: Post)
    }
}
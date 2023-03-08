package com.example.finalapp.presentation.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.example.finalapp.databinding.ItemRecentRecipesBinding
import com.example.finalapp.domain.model.Post
import com.example.finalapp.presentation.adapters.PostAdapter
import com.squareup.picasso.Picasso

class PostViewHolder(
    val binding: ItemRecentRecipesBinding
): RecyclerView.ViewHolder(binding.root){
    fun bind(post: Post,listener: PostAdapter.Listener) = binding.apply{
        txtName.text = post.postTitle
        txtDesc.text = post.post_description
        textPrepTime.text = post.post_preptime
        textView2.text = post.post_cooktime
        txtCooked.text = post.kkal
        Picasso.get().load(post.post_image.url).into(img)
        itemView.setOnClickListener {
            listener.onClick(post)
        }
    }
}
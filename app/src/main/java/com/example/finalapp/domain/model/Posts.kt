package com.example.finalapp.domain.model

import com.google.gson.annotations.SerializedName

data class Posts(
    @SerializedName("results")
    val posts: List<Post>
)

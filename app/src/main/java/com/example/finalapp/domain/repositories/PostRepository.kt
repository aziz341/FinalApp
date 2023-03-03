package com.example.finalapp.domain.repositories

import com.example.finalapp.domain.model.Post
import com.example.finalapp.domain.model.Posts
import retrofit2.Response

interface PostRepository {
    suspend fun createPost(post: Post):Response<Post>
    suspend fun getAllPosts():Response<Posts>
}
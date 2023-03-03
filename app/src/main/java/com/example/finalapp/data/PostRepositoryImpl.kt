package com.example.finalapp.data

import com.example.finalapp.domain.repositories.PostRepository
import com.example.finalapp.domain.utils.Utils
import com.example.finalapp.domain.model.Post
import com.example.finalapp.domain.model.Posts
import retrofit2.Response

object PostRepositoryImpl : PostRepository {
    override suspend fun createPost(post: Post): Response<Post> {
        return RetrofitInstanse.api.createPost(Utils.CONTENT_TYPE, post)
    }

    override suspend fun getAllPosts(): Response<Posts>{
        return RetrofitInstanse.api.getAllPost()
    }
}
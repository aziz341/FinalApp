package com.example.finalapp.domain.usecase

import com.example.finalapp.data.PostRepositoryImpl
import com.example.finalapp.domain.model.Post

class CreatePostUseCase(private val repository: PostRepositoryImpl) {
    suspend fun execute(post: Post) = repository.createPost(post)
}
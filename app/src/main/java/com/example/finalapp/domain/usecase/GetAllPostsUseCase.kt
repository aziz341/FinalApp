package com.example.finalapp.domain.usecase

import com.example.finalapp.domain.repositories.PostRepository

class GetAllPostsUseCase(private val repository: PostRepository) {
    suspend fun execute()=repository.getAllPosts()
}
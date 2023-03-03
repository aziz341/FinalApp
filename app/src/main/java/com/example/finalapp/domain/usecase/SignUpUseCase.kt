package com.example.finalapp.domain.usecase

import com.example.finalapp.data.RepositoryImpl
import com.example.finalapp.domain.model.User

class SignUpUseCase(private val repository: RepositoryImpl) {
    suspend fun execute(user: User) = repository.signUp(user)
}
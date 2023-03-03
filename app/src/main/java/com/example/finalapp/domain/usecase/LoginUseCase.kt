package com.example.finalapp.domain.usecase

import com.example.finalapp.data.RepositoryImpl

class LoginUseCase (private val repository: RepositoryImpl){
    suspend fun execute(username:String,password:String) = repository.login(username,password)
}
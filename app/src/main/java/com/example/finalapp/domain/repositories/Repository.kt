package com.example.finalapp.domain.repositories

import com.example.finalapp.domain.model.User
import retrofit2.Response

interface Repository {
suspend fun signUp(user: User):Response<User>
suspend fun login(username:String,password:String): Response<User>
}
package com.example.finalapp.data

import android.util.Log
import com.example.finalapp.domain.model.User
import com.example.finalapp.domain.repositories.Repository
import com.example.finalapp.domain.utils.Utils.CONTENT_TYPE
import retrofit2.Response

object RepositoryImpl: Repository {

    override suspend fun signUp(user: User): Response<User> {
        Log.i("joseph","RepositoryImpl -> signUp")

        return RetrofitInstanse.api.signUp(
            1,CONTENT_TYPE,user
        )
    }

    override suspend fun login(username: String, password: String): Response<User> {
        return RetrofitInstanse.api.login(1,username,password)
    }

}
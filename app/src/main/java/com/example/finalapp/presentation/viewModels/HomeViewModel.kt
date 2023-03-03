package com.example.finalapp.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalapp.data.PostRepositoryImpl
import com.example.finalapp.domain.model.Posts
import com.example.finalapp.domain.usecase.GetAllPostsUseCase
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel:ViewModel() {
    private val repository = PostRepositoryImpl
    private val getAllPostsUseCase = GetAllPostsUseCase(repository)

    private val _posts:MutableLiveData<Response<Posts>> = MutableLiveData()
    val posts:LiveData<Response<Posts>> get() = _posts

    fun getAllPosts() = viewModelScope.launch{
        _posts.value = getAllPostsUseCase.execute()
    }
}
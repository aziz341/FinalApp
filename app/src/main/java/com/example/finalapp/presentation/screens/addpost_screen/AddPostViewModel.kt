package com.example.finalapp.presentation.screens.addpost_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalapp.data.PostRepositoryImpl
import com.example.finalapp.domain.model.Post
import com.example.finalapp.domain.usecase.CreatePostUseCase
import kotlinx.coroutines.launch
import retrofit2.Response

class AddPostViewModel:ViewModel() {
    private val repository = PostRepositoryImpl
    private val createPostUseCase = CreatePostUseCase(repository)

    private val _response :MutableLiveData<Response<Post>> = MutableLiveData()
    val response:LiveData<Response<Post>> get() = _response

    fun createPost(post: Post) = viewModelScope.launch {
        _response.value = createPostUseCase.execute(post)
    }
}
package com.example.finalapp.presentation.screens.register_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalapp.data.RepositoryImpl
import com.example.finalapp.domain.model.User
import com.example.finalapp.domain.usecase.SignUpUseCase
import kotlinx.coroutines.launch
import retrofit2.Response

class RegisterViewModel:ViewModel() {
    private val repository = RepositoryImpl
    private val signUpUseCase = SignUpUseCase(repository)


    private val _state : MutableLiveData<Response<User>> = MutableLiveData()
    val state:LiveData<Response<User>> get() = _state


    private val _error:MutableLiveData<Exception> = MutableLiveData()
    val error:LiveData<Exception>get() = _error

    fun signUp(user: User) = viewModelScope.launch {
        try {
           _state.value = signUpUseCase.
           execute(user)
            Log.i("joseph","RegisterViewModel -> signUp")
        }catch (e:Exception){
            Log.d("MYAPP", e.message.toString())
            _error.value = e
        }
    }
}
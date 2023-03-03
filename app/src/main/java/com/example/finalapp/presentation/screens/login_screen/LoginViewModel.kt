package com.example.finalapp.presentation.screens.login_screen


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalapp.data.RepositoryImpl
import com.example.finalapp.domain.model.User
import com.example.finalapp.domain.usecase.LoginUseCase
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel : ViewModel() {
    private val repository = RepositoryImpl
    private val loginUseCase = LoginUseCase(repository)

    private val _state: MutableLiveData<Response<User>> = MutableLiveData()
    val state: LiveData<Response<User>> get() = _state

    private val _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> get() = _error

    fun login(username: String, password: String) =
        viewModelScope.launch {
            try {
                _state.value = loginUseCase.execute(username, password)
            } catch (e: Exception) {
                _error.value = e
            }
        }
}
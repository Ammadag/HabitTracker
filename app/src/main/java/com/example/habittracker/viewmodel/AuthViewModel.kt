package com.example.habittracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel: ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()


    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean>
        get() = _loginResult

    private val _signupResult = MutableLiveData<Boolean>()
    val signupResult: LiveData<Boolean>
        get() = _signupResult

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                _loginResult.value =  result.user != null
            } catch (e: Exception) {
                _loginResult.value = false
            }
        }
    }

    fun signUpUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                _signupResult.value = result.user != null
            } catch (e: Exception) {
                _signupResult.value = false
            }
        }
    }
}

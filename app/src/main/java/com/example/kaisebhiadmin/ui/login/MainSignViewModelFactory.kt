package com.example.kaisebhiadmin.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kaisebhiadmin.data.MainRepository

class MainSignViewModelFactory(private val repository: MainRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignInViewMode(repository) as T
    }
}

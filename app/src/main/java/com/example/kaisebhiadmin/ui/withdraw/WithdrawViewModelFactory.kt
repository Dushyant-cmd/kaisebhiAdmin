package com.example.kaisebhiadmin.ui.withdraw

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kaisebhiadmin.data.MainRepository

class WithdrawViewModelFactory(private val mainRepository: MainRepository): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return WithdrawViewModel(mainRepository) as T
    }
}
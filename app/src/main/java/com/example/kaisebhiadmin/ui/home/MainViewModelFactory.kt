package com.example.kaisebhiadmin.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kaisebhiadmin.data.MainRepository

class MainViewModelFactory(private val context: Context, repo: MainRepository):
    ViewModelProvider.Factory {
    private var repo: MainRepository
    init {
        this.repo = repo
    }
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repo) as T
    }
}
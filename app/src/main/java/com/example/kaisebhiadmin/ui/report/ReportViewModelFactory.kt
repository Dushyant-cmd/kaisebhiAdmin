package com.example.kaisebhiadmin.ui.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kaisebhiadmin.data.MainRepository

class ReportViewModelFactory(private val repo: MainRepository): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(model: Class<T>): T {
        return ReportViewModel(repo) as T
    }
}
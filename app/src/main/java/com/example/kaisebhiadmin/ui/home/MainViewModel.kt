package com.example.kaisebhiadmin.ui.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaisebhiadmin.data.MainRepository
import kotlinx.coroutines.launch

class MainViewModel(private val context: Context,
    private val repo: MainRepository): ViewModel() {
    //LiveData which stores all the data of View(MainActivity)
    val liveData: MutableLiveData<String> = MutableLiveData()

    //test method
    fun getQues() {
        viewModelScope.launch {
            liveData.value = repo.getAllQuestions()
        }
    }
}
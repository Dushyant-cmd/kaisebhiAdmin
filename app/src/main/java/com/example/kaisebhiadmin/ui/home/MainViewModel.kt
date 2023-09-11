package com.example.kaisebhiadmin.ui.home

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaisebhiadmin.data.MainRepository
import com.example.kaisebhiadmin.utils.ResponseClass
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repo: MainRepository): ViewModel() {
    //LiveData which stores all the data of View(MainActivity)
    val quesLiveData: MutableLiveData<ResponseClass> = MutableLiveData()

    //test method
    fun getQues() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllQuestions()
        }

        repo.quesLiveData.observeForever {
            quesLiveData.value = it
        }
    }
}
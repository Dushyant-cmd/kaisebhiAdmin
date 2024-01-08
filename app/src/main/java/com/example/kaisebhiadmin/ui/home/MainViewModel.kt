package com.example.kaisebhiadmin.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.kaisebhiadmin.data.MainRepository
import com.example.kaisebhiadmin.models.QuestionsModel
import com.example.kaisebhiadmin.utils.ResponseClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repo: MainRepository) : ViewModel() {
    //LiveData which stores all the data of View(MainActivity)
    val updateLiveData: MutableLiveData<ResponseClass> = MutableLiveData()
    fun updateQues(docId: String, status: String, position: Int, qualityCheck: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateQues(docId, status, position, qualityCheck)
        }
        repo.updateLivedata.observeForever {
            updateLiveData.value = it
        }
    }

    //test method
    suspend fun getPendingQues(filterBy: String, limit: Long): LiveData<PagingData<QuestionsModel>> {
        return repo.getPendingQues(filterBy, limit).cachedIn(viewModelScope)
    }

    suspend fun getFailQues(filterBy: String, limit: Long): LiveData<PagingData<QuestionsModel>> {
        return repo.getFailQues(filterBy, limit).cachedIn(viewModelScope)
    }

    suspend fun getPassQues(filterBy: String, limit: Long): LiveData<PagingData<QuestionsModel>> {
        return repo.getPassQues(filterBy, limit).cachedIn(viewModelScope)
    }

}
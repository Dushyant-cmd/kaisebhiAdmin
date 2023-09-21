package com.example.kaisebhiadmin.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaisebhiadmin.data.MainRepository
import com.example.kaisebhiadmin.models.QuestionsModel
import com.example.kaisebhiadmin.utils.ResponseClass
import com.example.kaisebhiadmin.utils.Success
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repo: MainRepository) : ViewModel() {
    //LiveData which stores all the data of View(MainActivity)
    val pendingQuesLiveData: MutableLiveData<ResponseClass> = MutableLiveData()
    val failQuesLiveData: MutableLiveData<ResponseClass> = MutableLiveData()
    val passQuesLiveData: MutableLiveData<ResponseClass> = MutableLiveData()
    val updateLiveData: MutableLiveData<ResponseClass> = MutableLiveData()
    fun updateQues(docId: String, status: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateQues(docId, status)
        }
        repo.updateLivedata.observeForever {
            updateLiveData.value = it
        }
    }
    //test method
    fun getPendingQues(filterBy: String, limit: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getPendingQues(filterBy, limit)
        }

        repo.pendingQuesLiveData.observeForever {
                if(it is Success<*>) {
                    val formattedList: ArrayList<QuestionsModel> =
                        ((it as Success<DocumentSnapshot>).response as ArrayList<DocumentSnapshot>)
                            .map { d: DocumentSnapshot ->
                                QuestionsModel(
                                    d.getString("id"),
                                    d.getString("title"),
                                    d.getString("desc"),
                                    d.getString("qpic"),
                                    d.getString("uname"),
                                    "NA",
                                    d.getBoolean("checkFav"),
                                    d.getString("likes"),
                                    d.getBoolean("checkLike"),
                                    d.getString("tanswers"),
                                    d.getString("likedByUser"),
                                    d.getString("image"),
                                    d.getString("userId"),
                                    d.getString("userPicUrl"),
                                    d.getString("imageRef"),
                                    d.getString("portal"),
                                    d.getString("audio"),
                                    d.getString("audioRef"),
                                    d.getString("qualityCheck"))
                            } as ArrayList<QuestionsModel>
                    pendingQuesLiveData.value = Success(formattedList)
                    Log.d("ViewModel.kt", "getPending: $formattedList")
                } else {
                    pendingQuesLiveData.value = it
                }
        }
    }
    fun getFailQues(filterBy: String, limit: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getFailQues(filterBy, limit)
        }

        repo.failQuesLiveData.observeForever {
            if(it is Success<*>) {
                val formattedList: ArrayList<QuestionsModel> =
                    ((it as Success<DocumentSnapshot>).response as ArrayList<DocumentSnapshot>)
                        .map { d: DocumentSnapshot ->
                            QuestionsModel(
                                d.getString("id"),
                                d.getString("title"),
                                d.getString("desc"),
                                d.getString("qpic"),
                                d.getString("uname"),
                                "NA",
                                d.getBoolean("checkFav"),
                                d.getString("likes"),
                                d.getBoolean("checkLike"),
                                d.getString("tanswers"),
                                d.getString("likedByUser"),
                                d.getString("image"),
                                d.getString("userId"),
                                d.getString("userPicUrl"),
                                d.getString("imageRef"),
                                d.getString("portal"),
                                d.getString("audio"),
                                d.getString("audioRef"),
                                d.getString("qualityCheck"))
                        } as ArrayList<QuestionsModel>
                failQuesLiveData.value = Success(formattedList)
                Log.d("ViewModel.kt", "getFail: $formattedList")
            } else {
                failQuesLiveData.value = it
            }
        }
    }
    fun getPassQues(filterBy: String, limit: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getPassQues(filterBy, limit)
        }

        repo.passQuesLiveData.observeForever {
            if(it is Success<*>) {
                val formattedList: ArrayList<QuestionsModel> =
                    ((it as Success<DocumentSnapshot>).response as ArrayList<DocumentSnapshot>)
                        .map { d: DocumentSnapshot ->
                            QuestionsModel(
                                d.getString("id"),
                                d.getString("title"),
                                d.getString("desc"),
                                d.getString("qpic"),
                                d.getString("uname"),
                                "NA",
                                d.getBoolean("checkFav"),
                                d.getString("likes"),
                                d.getBoolean("checkLike"),
                                d.getString("tanswers"),
                                d.getString("likedByUser"),
                                d.getString("image"),
                                d.getString("userId"),
                                d.getString("userPicUrl"),
                                d.getString("imageRef"),
                                d.getString("portal"),
                                d.getString("audio"),
                                d.getString("audioRef"),
                                d.getString("qualityCheck"))
                        } as ArrayList<QuestionsModel>
                passQuesLiveData.value = Success(formattedList)
                Log.d("ViewModel.kt", "getPass: $formattedList")
            } else {
                passQuesLiveData.value = it
            }
        }
    }

}
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
    val pendingQuesLiveData: MutableLiveData<ResponseClass> = MutableLiveData()
    val failQuesLiveData: MutableLiveData<ResponseClass> = MutableLiveData()
    val passQuesLiveData: MutableLiveData<ResponseClass> = MutableLiveData()
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
//        viewModelScope.launch(Dispatchers.IO) {
//            repo.getPendingQues(filterBy, limit)
//        }
//        repo.pendingQuesLiveData.observeForever {
//            if (it is Success<*>) {
//                val list = (it as Success<ArrayList<DocumentSnapshot>>).response
//                lastPendingDoc = list[list.size - 1]
//                val formattedList: ArrayList<QuestionsModel> =
//                        list.map { d: DocumentSnapshot ->
//                            QuestionsModel(
//                                d.getString("id"),
//                                d.getString("title"),
//                                d.getString("desc"),
//                                d.getString("qpic"),
//                                d.getString("uname"),
//                                "NA",
//                                d.getBoolean("checkFav"),
//                                d.getString("likes"),
//                                d.getBoolean("checkLike"),
//                                d.getString("tanswers"),
//                                d.getString("likedByUser"),
//                                d.getString("image") ?: "NA",
//                                d.getString("imageRef"),
//                                d.getString("userId"),
//                                d.getString("userPicUrl") ?: "NA",
//                                d.getString("portal"),
//                                d.getString("audio"),
//                                d.getString("audioRef"),
//                                d.getString("quesImgPath") ?: "NA",
//                                d.getString("qualityCheck")
//                            )
//                        } as ArrayList<QuestionsModel>
//                pendingQuesLiveData.value = Success(formattedList)
//                Log.d("ViewModel.kt", "getPending: $formattedList")
//            } else {
//                pendingQuesLiveData.value = it
//            }
//        }
    }

    suspend fun getFailQues(filterBy: String, limit: Long): LiveData<PagingData<QuestionsModel>> {
        return repo.getFailQues(filterBy, limit).cachedIn(viewModelScope)
//        viewModelScope.launch(Dispatchers.IO) {
//            repo.getFailQues(filterBy, limit)
//        }
//
//        repo.failQuesLiveData.observeForever {
//            if (it is Success<*>) {
//                val list = (it as Success<ArrayList<DocumentSnapshot>>).response
//                lastFailDoc = list[list.size - 1]
//                val formattedList: ArrayList<QuestionsModel> =
//                    list.map { d: DocumentSnapshot ->
//                            QuestionsModel(
//                                d.getString("id"),
//                                d.getString("title"),
//                                d.getString("desc"),
//                                d.getString("qpic"),
//                                d.getString("uname"),
//                                "NA",
//                                d.getBoolean("checkFav"),
//                                d.getString("likes"),
//                                d.getBoolean("checkLike"),
//                                d.getString("tanswers"),
//                                d.getString("likedByUser"),
//                                d.getString("image") ?: "NA",
//                                d.getString("imageRef"),
//                                d.getString("userId"),
//                                d.getString("userPicUrl") ?: "NA",
//                                d.getString("portal"),
//                                d.getString("audio"),
//                                d.getString("audioRef"),
//                                d.getString("quesImgPath") ?: "NA",
//                                d.getString("qualityCheck")
//                            )
//                        } as ArrayList<QuestionsModel>
//                failQuesLiveData.value = Success(formattedList)
//                Log.d("ViewModel.kt", "getFail: $formattedList")
//            } else {
//                failQuesLiveData.value = it
//            }
//        }
    }

    suspend fun getPassQues(filterBy: String, limit: Long): LiveData<PagingData<QuestionsModel>> {
        return repo.getPassQues(filterBy, limit).cachedIn(viewModelScope)
//        viewModelScope.launch(Dispatchers.IO) {
//            repo.getPassQues(filterBy, limit)
//        }
//
//        repo.passQuesLiveData.observeForever {
//            if (it is Success<*>) {
//                val list = (it as Success<ArrayList<DocumentSnapshot>>).response
//                lastPassDoc = list[list.size - 1]
//                val formattedList: ArrayList<QuestionsModel> =
//                    list.map { d: DocumentSnapshot ->
//                            QuestionsModel(
//                                d.getString("id"),
//                                d.getString("title"),
//                                d.getString("desc"),
//                                d.getString("qpic"),
//                                d.getString("uname"),
//                                "NA",
//                                d.getBoolean("checkFav"),
//                                d.getString("likes"),
//                                d.getBoolean("checkLike"),
//                                d.getString("tanswers"),
//                                d.getString("likedByUser"),
//                                d.getString("image") ?: "NA",
//                                d.getString("imageRef"),
//                                d.getString("userId"),
//                                d.getString("userPicUrl") ?: "NA",
//                                d.getString("portal"),
//                                d.getString("audio"),
//                                d.getString("audioRef"),
//                                d.getString("quesImgPath") ?: "NA",
//                                d.getString("qualityCheck")
//                            )
//                        } as ArrayList<QuestionsModel>
//                passQuesLiveData.value = Success(formattedList)
//                Log.d("ViewModel.kt", "getPass: $formattedList")
//            } else {
//                passQuesLiveData.value = it
//            }
//        }
    }

}
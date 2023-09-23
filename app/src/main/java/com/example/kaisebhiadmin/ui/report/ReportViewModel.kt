package com.example.kaisebhiadmin.ui.report

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaisebhiadmin.data.MainRepository
import com.example.kaisebhiadmin.models.ReportedModel
import com.example.kaisebhiadmin.utils.ResponseClass
import com.example.kaisebhiadmin.utils.Success
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReportViewModel(private val repo: MainRepository): ViewModel() {
    private val TAG = "ReportedViewModel.kt"
    val reportAnsLiveData = MutableLiveData<ResponseClass>()
    val deleteAnsLiveData = MutableLiveData<ResponseClass>()

    /**Below method will get the reported answers */
    fun getReportedAnswers(limit: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getReportedAnswers(limit)
        }

        repo.reportAnsLiveData.observeForever {res: ResponseClass ->
            if(res is Success<*>) {
                val extractRes = (res as Success<ArrayList<DocumentSnapshot>>).response
                    .map {
                        ReportedModel(it.id,
                            it.getString("title"),
                            it.getString("qdesc"),
                            it.getString("answer"),
                            it.getString("reportBy"))
                    }

                reportAnsLiveData.value = Success(extractRes as ArrayList<ReportedModel>)
                Log.d(TAG, "getReportedAnswers: $extractRes")
            } else reportAnsLiveData.value = res
        }
    }

    fun deleteAnswer(docId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteAnswer(docId)
        }

        repo.deleteAnsLiveData.observeForever {
            deleteAnsLiveData.value = it
        }
    }
}
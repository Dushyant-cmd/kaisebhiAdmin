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

    /**Below method will get the reported answers */
    fun getReportedAnswers() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getReportedAnswers()
        }

        repo.reportAnsLiveData.observeForever {res: ResponseClass ->
            if(res is Success<*>) {
                val extractRes = (res as Success<ArrayList<DocumentSnapshot>>).response
                    .map {
                        ReportedModel(it.id,
                            it.getString("title"),
                            it.getString("ques"),
                            it.getString("reportBy"))
                    }
                Log.d(TAG, "getReportedAnswers: ${(res).response}")
            } else reportAnsLiveData.value = res
        }
    }
}
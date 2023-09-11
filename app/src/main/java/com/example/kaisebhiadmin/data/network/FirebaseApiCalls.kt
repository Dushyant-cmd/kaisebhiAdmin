package com.example.kaisebhiadmin.data.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.kaisebhiadmin.utils.AppCustom
import com.example.kaisebhiadmin.utils.ResponseClass
import com.example.kaisebhiadmin.utils.ResponseError
import com.example.kaisebhiadmin.utils.Success
import com.google.android.gms.tasks.OnCompleteListener

class FirebaseApiCalls(private val application: AppCustom) {
    val quesLiveData = MutableLiveData<ResponseClass>()
    val adminLiveData = MutableLiveData<ResponseClass>()
    private val TAG = "FirebaseApi.kt"

    fun getQuesApi() {
        application.firestore.collection("questions")
            .get().addOnCompleteListener(OnCompleteListener {
                if (it.isSuccessful)
                    quesLiveData.value = Success(it.result.documents)
                else
                    quesLiveData.value = ResponseError(it.exception.toString())
            })
    }

    fun getAdminCred() {
        application.firestore.collection("appData")
            .document("admin").get().addOnCompleteListener(
                OnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d(TAG, "getAdminCred: $it")
                        adminLiveData.value = Success(it.result)
                    } else {
                        adminLiveData.value = ResponseError(it.exception.toString())
                        Log.d(TAG, "getAdminCred: ${it.exception}")
                    }
                }
            )
    }
}
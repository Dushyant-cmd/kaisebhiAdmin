package com.example.kaisebhiadmin.data.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.kaisebhiadmin.utils.AppCustom
import com.example.kaisebhiadmin.utils.ResponseClass
import com.example.kaisebhiadmin.utils.ResponseError
import com.example.kaisebhiadmin.utils.Success
import com.google.android.gms.tasks.OnCompleteListener

class FirebaseApiCalls(private val application: AppCustom) {
    val pendingQuesLiveData: MutableLiveData<ResponseClass> = MutableLiveData()
    val failQuesLiveData: MutableLiveData<ResponseClass> = MutableLiveData()
    val passQuesLiveData: MutableLiveData<ResponseClass> = MutableLiveData()
    val adminLiveData = MutableLiveData<ResponseClass>()
    val updateLiveData = MutableLiveData<ResponseClass>()
    private val TAG = "FirebaseApi.kt"

    fun getQuesApi(filterBy: String, limit: Long) {
        Log.d(TAG, "getQuesApi filter by: $filterBy")
        if(filterBy.equals("pending")) {
            application.firestore.collection("questions").whereEqualTo("qualityCheck", filterBy)
                .limit(limit)
                .get().addOnCompleteListener(OnCompleteListener {
                    if (it.isSuccessful) {
                        pendingQuesLiveData.value = Success(it.result.documents)
                        Log.d(TAG, "getQuesApi: ${it.result.documents}")
                    }
                    else {
                        pendingQuesLiveData.value = ResponseError(it.exception.toString())
                        Log.d(TAG, "getQuesApi: ${it.exception}")
                    }
                })
        } else if(filterBy.equals("fail")) {
            application.firestore.collection("questions").whereEqualTo("qualityCheck", filterBy)
                .limit(limit)
                .get().addOnCompleteListener(OnCompleteListener {
                    if (it.isSuccessful) {
                        failQuesLiveData.value = Success(it.result.documents)
                        Log.d(TAG, "getQuesApi fail: ${it.result.documents}")
                    }
                    else {
                        failQuesLiveData.value = ResponseError(it.exception.toString())
                        Log.d(TAG, "getQuesApi fail: ${it.exception}")
                    }
                })
        } else if(filterBy.equals("pass")) {
            application.firestore.collection("questions").whereEqualTo("qualityCheck", filterBy)
                .limit(limit)
                .get().addOnCompleteListener(OnCompleteListener {
                    if (it.isSuccessful) {
                        passQuesLiveData.value = Success(it.result.documents)
                        Log.d(TAG, "getQuesApi pass: ${it.result.documents}")
                    }
                    else {
                        passQuesLiveData.value = ResponseError(it.exception.toString())
                        Log.d(TAG, "getQuesApi pass: ${it.exception}")
                    }
                })
        }
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

    fun updateStatus(docId: String, status: String, pos: Int, qualityCheck: String) {
        val map = mapOf("qualityCheck" to status)
        application.firestore.collection("questions")
            .document(docId).update(map)
            .addOnSuccessListener {
                updateLiveData.value = Success<Map<String, String>>(mapOf("docId" to docId,
                    "status" to status,
                    "pos" to pos.toString(),
                    "qualityCheck" to qualityCheck))
            }.addOnFailureListener {
                updateLiveData.value = ResponseError(it.toString())
            }
    }
}
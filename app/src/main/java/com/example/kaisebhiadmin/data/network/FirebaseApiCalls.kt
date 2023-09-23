package com.example.kaisebhiadmin.data.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.kaisebhiadmin.utils.AppCustom
import com.example.kaisebhiadmin.utils.ResponseClass
import com.example.kaisebhiadmin.utils.ResponseError
import com.example.kaisebhiadmin.utils.Success
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseApiCalls(private val application: AppCustom) {
    val pendingQuesLiveData: MutableLiveData<ResponseClass> = MutableLiveData()
    val failQuesLiveData: MutableLiveData<ResponseClass> = MutableLiveData()
    val passQuesLiveData: MutableLiveData<ResponseClass> = MutableLiveData()
    val adminLiveData = MutableLiveData<ResponseClass>()
    val updateLiveData = MutableLiveData<ResponseClass>()
    val reportAnsLiveData = MutableLiveData<ResponseClass>()
    val deleteAnsLiveData = MutableLiveData<ResponseClass>()
    private val TAG = "FirebaseApi.kt"

    suspend fun getQuesApi(filterBy: String, limit: Long) {
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

    suspend fun getAdminCred() {
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

    suspend fun updateStatus(docId: String, status: String, pos: Int, qualityCheck: String) {
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

    suspend fun getReportedAnswers(limit: Long) {
        application.firestore.collection("answers")
            .whereEqualTo("userReportCheck", true).limit(limit).get()
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    reportAnsLiveData.value = Success<ArrayList<DocumentSnapshot>>(it.result.documents as ArrayList<DocumentSnapshot>)
                } else {
                    reportAnsLiveData.value = ResponseError(it.exception.toString())
                }
            }
    }

    suspend fun deleteAnswer(docId: String) {
        application.firestore.collection("answers").document(docId)
            .delete().addOnSuccessListener {
                deleteAnsLiveData.value = Success("Answer Deleted Successfully")
            }.addOnFailureListener {
                deleteAnsLiveData.value = ResponseError(it.toString())
            }
    }

    fun updateFcmTokens(currToken: String) {
        val map = mapOf("adminFcmTokens" to currToken)
        FirebaseFirestore.getInstance().collection("appData")
            .document("admin").update(map)
            .addOnCompleteListener {
                Log.d(TAG, "setObservers: ${it.result}")
            }
    }
}
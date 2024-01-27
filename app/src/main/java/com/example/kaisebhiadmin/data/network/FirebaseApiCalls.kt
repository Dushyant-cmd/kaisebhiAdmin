package com.example.kaisebhiadmin.data.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.kaisebhiadmin.utils.AppCustom
import com.example.kaisebhiadmin.utils.ResponseClass
import com.example.kaisebhiadmin.utils.ResponseError
import com.example.kaisebhiadmin.utils.Success
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

class FirebaseApiCalls(private val application: AppCustom) {
    val adminLiveData = MutableLiveData<ResponseClass>()
    val updateLiveData = MutableLiveData<ResponseClass>()
    val deleteAnsLiveData = MutableLiveData<ResponseClass>()
    private val TAG = "FirebaseApi.kt"

    suspend fun getQuesApi(
        filterBy: String,
        limit: Long,
        lastDoc: DocumentSnapshot?
    ): Task<QuerySnapshot> {
        Log.d(TAG, "getQuesApi filter by: $filterBy")
        if (lastDoc == null) {
            Log.d(TAG, "getQuesApi1 $lastDoc")
            return application.firestore.collection("questions")
                .whereEqualTo("qualityCheck", filterBy)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(limit)
                .get()
        } else {
            Log.d(TAG, "getQuesApi2 $lastDoc")
            return application.firestore.collection("questions")
                .whereEqualTo("qualityCheck", filterBy)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .startAfter(lastDoc)
                .limit(limit)
                .get()
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
                updateLiveData.value = Success<Map<String, String>>(
                    mapOf(
                        "docId" to docId,
                        "status" to status,
                        "pos" to pos.toString(),
                        "qualityCheck" to qualityCheck
                    )
                )
            }.addOnFailureListener {
                updateLiveData.value = ResponseError(it.toString())
            }
    }

    suspend fun getReportedAnswers(limit: Long, lastDoc: DocumentSnapshot?): Task<QuerySnapshot> {
        return if (lastDoc == null)
            application.firestore.collection("answers")
                .whereEqualTo("userReportCheck", true)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(limit)
                .get()
        else {
            application.firestore.collection("answers")
                .whereEqualTo("userReportCheck", true)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .startAfter(lastDoc)
                .limit(limit)
                .get()
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

    fun getWithdrawal(lastDoc: DocumentSnapshot, limit: Long): Task<QuerySnapshot> {
        return if (lastDoc == null) {
             application.firestore.collection("rewardHistory")
                .whereEqualTo("type", "withdraw")
                .limit(limit)
                .get()
        } else {
             application.firestore.collection("rewardHistory")
                .whereEqualTo("type", "withdraw")
                .startAfter(lastDoc)
                .limit(limit)
                .get()
        }
    }


}
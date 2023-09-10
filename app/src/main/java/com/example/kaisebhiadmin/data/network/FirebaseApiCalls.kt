package com.example.kaisebhiadmin.data.network

import android.util.Log
import com.example.kaisebhiadmin.utils.AppCustom
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class FirebaseApiCalls(private val application: AppCustom) {
    private val TAG = "FirebaseApi.kt"

    suspend fun getQuesApi(): ArrayList<DocumentSnapshot> {
        var list: ArrayList<DocumentSnapshot>? = ArrayList()
        val asyncCoroutine: Deferred<ArrayList<DocumentSnapshot>> = CoroutineScope(Dispatchers.IO).async {
            application.firestore.collection("questions")
                .get().addOnCompleteListener(OnCompleteListener {
                    list = it.result.documents as ArrayList<DocumentSnapshot>
                })
            list!!
        }

        return asyncCoroutine.await()
    }

    suspend fun getAdminCred(): DocumentSnapshot? {
        var doc: DocumentSnapshot? = null
        val asyncCor = CoroutineScope(Dispatchers.IO).async {
            application.firestore.collection("appData")
                .document("admin").get().addOnCompleteListener(
                    OnCompleteListener {
                        if(it.isSuccessful) {
                            Log.d(TAG, "getAdminCred: $it")
                            doc = it.result
                        } else
                            Log.d(TAG, "getAdminCred: ${it.exception}")
                    }
                )

            doc
        }

        Log.d(TAG, "getAdminCred: awaiting")
        return asyncCor.await()
    }
}
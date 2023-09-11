package com.example.kaisebhiadmin.data

import android.app.Activity
import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.kaisebhiadmin.data.network.FirebaseApiCalls
import com.example.kaisebhiadmin.utils.ResponseClass
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(private val firebaseInterface: FirebaseApiCalls) {
    val quesLiveData = MutableLiveData<ResponseClass>()
    val adminLiveData = MutableLiveData<ResponseClass>()
    /**Below method get all questions */
    suspend fun getAllQuestions() {
        firebaseInterface.getQuesApi()
        withContext(Dispatchers.Main) {
            firebaseInterface.quesLiveData.observeForever {
                quesLiveData.value = it
            }
        }
    }

    /**Below method will return admin firestore credentials */
    suspend fun getAdminCred() {
        firebaseInterface.getAdminCred()
        withContext(Dispatchers.Main) {
            firebaseInterface.adminLiveData.observeForever{
                adminLiveData.value = it
            }
        }
    }
}
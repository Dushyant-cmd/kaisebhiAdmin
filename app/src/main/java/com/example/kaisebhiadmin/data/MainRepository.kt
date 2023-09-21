package com.example.kaisebhiadmin.data

import androidx.lifecycle.MutableLiveData
import com.example.kaisebhiadmin.data.network.FirebaseApiCalls
import com.example.kaisebhiadmin.utils.ResponseClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(private val firebaseInterface: FirebaseApiCalls) {
    val pendingQuesLiveData: MutableLiveData<ResponseClass> = MutableLiveData()
    val failQuesLiveData: MutableLiveData<ResponseClass> = MutableLiveData()
    val passQuesLiveData: MutableLiveData<ResponseClass> = MutableLiveData()
    val adminLiveData: MutableLiveData<ResponseClass> = MutableLiveData()
    val updateLivedata = MutableLiveData<ResponseClass>()

    /**Below method get pending questions */
    suspend fun getPendingQues(filterBy: String, limit: Long) {
        firebaseInterface.getQuesApi(filterBy, limit)
        withContext(Dispatchers.Main) {
            firebaseInterface.pendingQuesLiveData.observeForever {
                pendingQuesLiveData.value = it
            }
        }
    }
    /**Below method get fail questions */
    suspend fun getFailQues(filterBy: String, limit: Long) {
        firebaseInterface.getQuesApi(filterBy, limit)
        withContext(Dispatchers.Main) {
            firebaseInterface.failQuesLiveData.observeForever {
                failQuesLiveData.value = it
            }
        }
    }
    /**Below method get pass questions */
    suspend fun getPassQues(filterBy: String, limit: Long) {
        firebaseInterface.getQuesApi(filterBy, limit)
        withContext(Dispatchers.Main) {
            firebaseInterface.passQuesLiveData.observeForever {
                passQuesLiveData.value = it
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

    /**Below method will update status of question */
    suspend fun updateQues(docId: String, status: String) {
        firebaseInterface.updateStatus(docId, status)
        withContext(Dispatchers.Main) {
            firebaseInterface.updateLiveData.observeForever {
                updateLivedata.value = it
            }
        }
    }
}
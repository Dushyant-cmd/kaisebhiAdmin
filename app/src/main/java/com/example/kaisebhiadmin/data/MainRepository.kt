package com.example.kaisebhiadmin.data

import androidx.lifecycle.MutableLiveData
import com.example.kaisebhiadmin.data.network.FirebaseApiCalls
import com.example.kaisebhiadmin.utils.ResponseClass
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(private val firebaseInterface: FirebaseApiCalls) {
    val pendingQuesLiveData: MutableLiveData<ResponseClass> = MutableLiveData()
    val failQuesLiveData: MutableLiveData<ResponseClass> = MutableLiveData()
    val passQuesLiveData: MutableLiveData<ResponseClass> = MutableLiveData()
    val adminLiveData: MutableLiveData<ResponseClass> = MutableLiveData()
    val updateLivedata = MutableLiveData<ResponseClass>()
    val reportAnsLiveData = MutableLiveData<ResponseClass>()
    val deleteAnsLiveData = MutableLiveData<ResponseClass>()

    /**Below method get pending questions */
    suspend fun getPendingQues(filterBy: String, limit: Long, lastDoc: DocumentSnapshot?) {
        firebaseInterface.getQuesApi(filterBy, limit, lastDoc)
        withContext(Dispatchers.Main) {
            firebaseInterface.pendingQuesLiveData.observeForever {
                pendingQuesLiveData.value = it
            }
        }
    }
    /**Below method get fail questions */
    suspend fun getFailQues(filterBy: String, limit: Long, lastDoc: DocumentSnapshot?) {
        firebaseInterface.getQuesApi(filterBy, limit, lastDoc)
        withContext(Dispatchers.Main) {
            firebaseInterface.failQuesLiveData.observeForever {
                failQuesLiveData.value = it
            }
        }
    }
    /**Below method get pass questions */
    suspend fun getPassQues(filterBy: String, limit: Long, lastDoc: DocumentSnapshot?) {
        firebaseInterface.getQuesApi(filterBy, limit, lastDoc)
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
    suspend fun updateQues(docId: String, status: String, pos: Int, qualityCheck:String) {
        firebaseInterface.updateStatus(docId, status, pos, qualityCheck)
        withContext(Dispatchers.Main) {
            firebaseInterface.updateLiveData.observeForever {
                updateLivedata.value = it
            }
        }
    }

    /**Below method to get all the reported answers */
    suspend fun getReportedAnswers(limit: Long) {
        firebaseInterface.getReportedAnswers(limit)
        withContext(Dispatchers.Main) {
            firebaseInterface.reportAnsLiveData.observeForever {
                reportAnsLiveData.value = it
            }
        }
    }

    /**Below method will delete the answer
     * @param docId document id of answer collection */
    suspend fun deleteAnswer(docId: String) {
        firebaseInterface.deleteAnswer(docId)
        withContext(Dispatchers.Main) {
            firebaseInterface.deleteAnsLiveData.observeForever {
                deleteAnsLiveData.value = it
            }
        }
    }

    fun updateFcmTokens(currToken: String) {
        firebaseInterface.updateFcmTokens(currToken)
    }
}
package com.example.kaisebhiadmin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.kaisebhiadmin.data.network.FirebaseApiCalls
import com.example.kaisebhiadmin.models.QuestionsModel
import com.example.kaisebhiadmin.models.ReportedModel
import com.example.kaisebhiadmin.pagingsource.FailPagingSource
import com.example.kaisebhiadmin.pagingsource.PassPagingSource
import com.example.kaisebhiadmin.pagingsource.PendingPagingSource
import com.example.kaisebhiadmin.pagingsource.ReportPagingSource
import com.example.kaisebhiadmin.utils.ResponseClass
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
    suspend fun getPendingQues(filterBy: String, limit: Long): LiveData<PagingData<QuestionsModel>> {
//        firebaseInterface.getQuesApi(filterBy, limit)
//        withContext(Dispatchers.Main) {
//            firebaseInterface.pendingQuesLiveData.observeForever {
//                pendingQuesLiveData.value = it
//            }
//        }
        return Pager(
            config = PagingConfig(pageSize = 10, maxSize = 100),
            pagingSourceFactory = { PendingPagingSource(firebaseInterface) }
        ).liveData
    }

    /**Below method get fail questions */
    suspend fun getFailQues(filterBy: String, limit: Long): LiveData<PagingData<QuestionsModel>> {
//        firebaseInterface.getQuesApi(filterBy, limit)
//        withContext(Dispatchers.Main) {
//            firebaseInterface.failQuesLiveData.observeForever {
//                failQuesLiveData.value = it
//            }
//        }
        return Pager(
            config = PagingConfig(pageSize = 10, maxSize = 100),
            pagingSourceFactory = { FailPagingSource(firebaseInterface) }
        ).liveData
    }

    /**Below method get pass questions */
    suspend fun getPassQues(filterBy: String, limit: Long): LiveData<PagingData<QuestionsModel>> {
//        firebaseInterface.getQuesApi(filterBy, limit)
//        withContext(Dispatchers.Main) {
//            firebaseInterface.passQuesLiveData.observeForever {
//                passQuesLiveData.value = it
//            }
//        }
        return Pager(
            config = PagingConfig(pageSize = 10, maxSize = 100),
            pagingSourceFactory = { PassPagingSource(firebaseInterface) }
        ).liveData
    }

    /**Below method will return admin firestore credentials */
    suspend fun getAdminCred() {
        firebaseInterface.getAdminCred()
        withContext(Dispatchers.Main) {
            firebaseInterface.adminLiveData.observeForever {
                adminLiveData.value = it
            }
        }
    }

    /**Below method will update status of question */
    suspend fun updateQues(docId: String, status: String, pos: Int, qualityCheck: String) {
        firebaseInterface.updateStatus(docId, status, pos, qualityCheck)
        withContext(Dispatchers.Main) {
            firebaseInterface.updateLiveData.observeForever {
                updateLivedata.value = it
            }
        }
    }

    /**Below method to get all the reported answers */
    suspend fun getReportedAnswers(): LiveData<PagingData<ReportedModel>> {
//        firebaseInterface.getReportedAnswers(limit)
//        withContext(Dispatchers.Main) {
//            firebaseInterface.reportAnsLiveData.observeForever {
//                reportAnsLiveData.value = it
//            }
//        }
        return Pager(
            config = PagingConfig(pageSize = 2, maxSize = 100),
            pagingSourceFactory = { ReportPagingSource(firebaseInterface) }
        ).liveData
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
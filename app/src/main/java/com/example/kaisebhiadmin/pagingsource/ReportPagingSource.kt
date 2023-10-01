package com.example.kaisebhiadmin.pagingsource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kaisebhiadmin.data.network.FirebaseApiCalls
import com.example.kaisebhiadmin.models.ReportedModel
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.tasks.await

class ReportPagingSource(private val firebase: FirebaseApiCalls) :
    PagingSource<Int, ReportedModel>() {
//    private var lastDoc: DocumentSnapshot? = null
    private val TAG = "ReportPagingSource.kt"
    private var lastDoc: DocumentSnapshot? = null
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReportedModel> {
        return try {
            //Here we do api call and return LoadResult.Page instance.
            val position = params.key ?: 1
            val apiCall = firebase.getReportedAnswers(10, lastDoc)
            lateinit var loadResult: LoadResult<Int, ReportedModel>
            apiCall.addOnSuccessListener {
                if(it.documents.size > 0) {
                    lastDoc = it.documents[it.documents.size - 1]
                    val extractRes = it.documents.map {
                        ReportedModel(
                            it.id,
                            it.getString("title"),
                            it.getString("qdesc"),
                            it.getString("answer"),
                            it.getString("reportBy")
                        )
                    }
                    Log.d(TAG, "load: $extractRes")
                    loadResult = LoadResult.Page(
                        data = extractRes,
                        prevKey = if (position == 1) null else position.minus(1),
                        nextKey = if (position == 100) null else position.plus(1)
                    )
                } else {
                    loadResult = LoadResult.Page(
                        data = listOf(),
                        prevKey = null,
                        nextKey = null
                    )
                }
            }

            apiCall.addOnFailureListener {
                Log.d(TAG, "load: $it")
                loadResult = LoadResult.Error(it)
            }
            //Below line is to wait till the firebase api call is completed.
            Tasks.whenAll(apiCall).await()
            Log.d(TAG, "load: $loadResult")
            loadResult
        } catch (e: Exception) {
            Log.d(TAG, "load error: $e")
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ReportedModel>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}
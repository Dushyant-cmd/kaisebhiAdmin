package com.example.kaisebhiadmin.pagingsource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kaisebhiadmin.data.network.FirebaseApiCalls
import com.example.kaisebhiadmin.models.QuestionsModel
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.tasks.await

class FailPagingSource(private val firebaseApiCalls: FirebaseApiCalls) :
    PagingSource<Int, QuestionsModel>() {
    private val TAG = "FailPagingSource.kt"
    private var lastDoc: DocumentSnapshot? = null
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, QuestionsModel> {
        return try {
            val pos = params.key ?: 1
            val apiCalls = firebaseApiCalls.getQuesApi("fail", 10, lastDoc)
            lateinit var loadResult: LoadResult<Int, QuestionsModel>
            apiCalls.addOnSuccessListener {
                if (it.documents.size > 0) {
                    lastDoc = it.documents[0]
                    val formattedList: ArrayList<QuestionsModel> =
                        it.documents.map { d: DocumentSnapshot ->
                            QuestionsModel(
                                d.getString("id"),
                                d.getString("title"),
                                d.getString("desc"),
                                d.getString("qpic"),
                                d.getString("uname"),
                                "NA",
                                d.getBoolean("checkFav"),
                                d.getString("likes"),
                                d.getBoolean("checkLike"),
                                d.getString("tanswers"),
                                d.getString("likedByUser"),
                                d.getString("image") ?: "NA",
                                d.getString("imageRef"),
                                d.getString("userId"),
                                d.getString("userPicUrl") ?: "NA",
                                d.getString("portal"),
                                d.getString("audio"),
                                d.getString("audioRef"),
                                d.getString("quesImgPath") ?: "NA",
                                d.getString("qualityCheck")
                            )
                        } as ArrayList<QuestionsModel>
                    loadResult = LoadResult.Page(
                        data = formattedList,
                        prevKey = if (pos == 1) null else pos.minus(1),
                        nextKey = if (pos == 100) null else pos.plus(1)
                    )
                } else {
                    loadResult = LoadResult.Error(java.lang.Exception("Data is not available"))
                }
            }

            apiCalls.addOnFailureListener {
                loadResult = LoadResult.Error(it)
            }

            Tasks.whenAll(apiCalls).await()
            Log.d(TAG, "load: ${(loadResult as LoadResult.Page).data}")
            loadResult
        } catch (e: Exception) {
            Log.d(TAG, "load: $e")
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, QuestionsModel>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.minus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.plus(1)
        }
    }
}
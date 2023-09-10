package com.example.kaisebhiadmin.data

import com.example.kaisebhiadmin.data.network.FirebaseApiCalls
import com.google.firebase.firestore.DocumentSnapshot

class MainRepository(private val firebaseInterface: FirebaseApiCalls) {
    /**Below method get all questions */
    suspend fun getAllQuestions(): ArrayList<DocumentSnapshot> {
        return firebaseInterface.getQuesApi()
    }

    /**Below method will return admin firestore credentials */
    suspend fun getAdminCred(): DocumentSnapshot? {
        return firebaseInterface.getAdminCred()
    }
}
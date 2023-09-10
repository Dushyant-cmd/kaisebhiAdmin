package com.example.kaisebhiadmin.utils

import android.app.Application
import com.example.kaisebhiadmin.data.localdb.SessionConfig
import com.example.kaisebhiadmin.data.network.FirebaseApiCalls
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class AppCustom: Application() {
    lateinit var firestore: FirebaseFirestore
    lateinit var storage: FirebaseStorage
    lateinit var firebaseApiClass: FirebaseApiCalls
    lateinit var sessionConfig: SessionConfig

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this@AppCustom)
        initialize()
    }

    private fun initialize() {
        sessionConfig = SessionConfig(applicationContext)
        firestore = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()
        firebaseApiClass = FirebaseApiCalls(this@AppCustom as AppCustom)
    }
}
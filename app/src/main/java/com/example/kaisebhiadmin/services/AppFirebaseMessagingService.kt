package com.example.kaisebhiadmin.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class AppFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        val map: Map<String, String> = message.data
        //Create a Notification Channel because of changes from android O(8)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(packageName.toString(), "notification", NotificationManager.IMPORTANCE_HIGH)
            channel.vibrationPattern = longArrayOf(100, 1000, 100, 100)
        }
    }

    override fun onNewToken(token: String) {
        //here you get new token if generated can also disable new auto generation
        //of token.
    }
}
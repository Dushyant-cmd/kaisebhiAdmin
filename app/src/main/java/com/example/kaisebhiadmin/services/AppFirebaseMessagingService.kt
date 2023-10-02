package com.example.kaisebhiadmin.services

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.kaisebhiadmin.R
import com.example.kaisebhiadmin.data.localdb.SessionConfig
import com.example.kaisebhiadmin.ui.home.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class AppFirebaseMessagingService : FirebaseMessagingService() {
    private var isForeground = false
    private var TAG = "FirebaseNotification.kt"
    override fun onMessageReceived(message: RemoteMessage) {
        try {
            isForeground = true
            message.notification?.let {
                showNotification(it.title.toString(), it.body.toString())
            }
        } catch (e: Exception) {
            Log.d(TAG, "onMessageReceived: $e")
        }
    }

    override fun handleIntent(intent: Intent) {
        try {
            intent.data?.let {
                if (!isForeground) {
                    showNotification(
                        intent.getStringExtra("title").toString(),
                        intent.getStringExtra("body").toString()
                    )
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "handleIntent: $e")
        }
    }

    private fun showNotification(title: String, body: String) {
        try {
            //Create a Notification Channel because of changes from android O(8)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    packageName.toString(),
                    "notification",
                    NotificationManager.IMPORTANCE_HIGH
                )
                channel.description = "channel"

                val notificationService =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationService.createNotificationChannel(channel)
            }
            //Crate Notification that gonna display when notification received
            val builder = NotificationCompat.Builder(this, packageName.toString())
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.logo_transparent)
                .setColor(resources.getColor(R.color.white))
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            val pendingIntent = PendingIntent.getActivity(
                this, 59, intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
            builder.setContentIntent(pendingIntent)

            val notificationManagerCompat = NotificationManagerCompat.from(this)
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                if (SessionConfig(this@AppFirebaseMessagingService).getLoginStatus())
                    notificationManagerCompat.notify(101, builder.build())
                Log.d(TAG, "handleIntent: ${intent.data}")
                return
            } else {
                Toast.makeText(this, "Allow notification permission", Toast.LENGTH_SHORT).show()
            }

            isForeground = false

        } catch (e: Exception) {
            Log.d(TAG, "showNotification: $e")
        }
    }

    override fun onNewToken(token: String) {
        //here you get new token if generated can also disable new auto generation
        //of token.
    }
}
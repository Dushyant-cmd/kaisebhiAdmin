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
        isForeground = true
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

        var title = "You got new question on Admin"
        var body = "Click here"
        message.notification?.let {
            title = it.title.toString()
            body = it.body.toString()
        }
        //Crate Notification that gonna display when notification received
        val builder = NotificationCompat.Builder(this, packageName.toString())
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSmallIcon(R.mipmap.ic_launcher);
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
            notificationManagerCompat.notify(101, builder.build())
            Log.d(TAG, "onMessageReceived: $isForeground")
            return
        } else {
            Toast.makeText(this, "Allow notification permission", Toast.LENGTH_SHORT).show()
        }
    }

    override fun handleIntent(intent: Intent) {
        intent?.let {
            if (!isForeground) {
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
                var title = "You got new question on Admin"
                var body = "Click here"
                intent.data?.let {
                    title = intent.getStringExtra("title").toString()
                    body = intent.getStringExtra("body").toString()
                }
                //Crate Notification that gonna display when notification received
                val builder = NotificationCompat.Builder(this, packageName.toString())
                    .setContentTitle(title)
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setSmallIcon(R.mipmap.ic_launcher);
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
            }
        }
    }

    override fun onNewToken(token: String) {
        //here you get new token if generated can also disable new auto generation
        //of token.
    }
}
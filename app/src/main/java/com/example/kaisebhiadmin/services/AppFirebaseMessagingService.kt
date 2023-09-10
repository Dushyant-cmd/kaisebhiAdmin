package com.example.kaisebhiadmin.services

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.kaisebhiadmin.R
import com.example.kaisebhiadmin.ui.home.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class AppFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        //Create a Notification Channel because of changes from android O(8)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(packageName.toString(), "notification", NotificationManager.IMPORTANCE_HIGH)
            channel.description = "channel"

            val notificationService = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationService.createNotificationChannel(channel)
        }

        //Crate Notification that gonna display when notification received
        val builder = NotificationCompat.Builder(this, packageName.toString())
            .setContentTitle("hello")
            .setContentText("description")
            .setAutoCancel(true)
            .setSmallIcon(R.mipmap.ic_launcher);
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        val pendingIntent = PendingIntent.getActivity(this, 59, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntent)

        val notificationManagerCompat = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            notificationManagerCompat.notify(101, builder.build())
            return
        } else {
            Toast.makeText(this, "Allow notification permission", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNewToken(token: String) {
        //here you get new token if generated can also disable new auto generation
        //of token.
    }
}
package com.arash.altafi.websocket5.utils

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.arash.altafi.websocket5.MainActivity
import com.arash.altafi.websocket5.R

object NotificationUtils {

    private const val CHANNEL_ID = "CHANNEL_ID"

    fun sendNotification(
        context: Context,
        notificationID: Int,
        title: String,
        description: String,
        image: Bitmap
    ) {
        val intent = Intent(context, MainActivity::class.java)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(
                notificationManager,
                soundUri = Uri.parse("android.resource://${context.packageName}/raw/notif")
            )
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("NotClick", true)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE
            else 0x0)
                    or
                    PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setLargeIcon(image)
            .setContentText(description)
            .setContentIntent(pendingIntent)

        notificationManager.notify(notificationID, notification.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(
        notificationManager: NotificationManager,
        soundUri: Uri? = null
    ) {
        val soundURI = soundUri ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val channelName = "channelName1"
        val channel = NotificationChannel(
            CHANNEL_ID,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            when (importance) {
                NotificationManager.IMPORTANCE_HIGH,
                NotificationManager.IMPORTANCE_DEFAULT -> {
                    setSound(soundURI, audioAttributes)
                }
                NotificationManager.IMPORTANCE_LOW -> {
                    if (soundUri != null)
                        setSound(soundURI, audioAttributes)
                }
            }
            description = "My channel description"
            enableLights(true)
            lightColor = Color.GREEN
        }
        notificationManager.createNotificationChannel(channel)
    }

}
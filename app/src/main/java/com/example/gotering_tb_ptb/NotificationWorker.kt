package com.example.gotering_tb_ptb

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.gotering_tb_ptb.activity.TransaksiActivity

class NotificationWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    private val notifId = inputData.getInt(NOTIF_ID, 0)
    private val notifTitle = inputData.getString(NOTIF_TITLE)
    private val notifContent = inputData.getString(NOTIF_CONTENT)

    override fun doWork(): Result {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
                .setContentTitle(notifTitle)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(getPendingIntent())
                .setContentText(notifContent)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            inputData.getString(NOTIFICATION_CHANNEL_ID),
            NotificationManager.IMPORTANCE_HIGH
        )
        notification.setChannelId(NOTIFICATION_CHANNEL_ID)
        notificationManager.createNotificationChannel(channel)
        notificationManager.notify(notifId, notification.build())
        return Result.success()
    }

    private fun getPendingIntent(): PendingIntent? {
        val intent = Intent(applicationContext, TransaksiActivity::class.java)

        return PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }

}

const val NOTIF_ID = "NOTIF_ID"
const val NOTIF_TITLE = "NOTIF_TITLE"
const val NOTIF_CONTENT = "NOTIF_CONTENT"
const val NOTIFICATION_CHANNEL_ID = "notify-channel"
const val NOTIF_UNIQUE_WORK: String = "NOTIF_UNIQUE_WORK"
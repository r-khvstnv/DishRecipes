package com.rkhvstnv.dishrecipes.utils

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.rkhvstnv.dishrecipes.R
import com.rkhvstnv.dishrecipes.ui.activities.MainActivity

class NotificationWorker(
    appContext: Context,
    workerParams: WorkerParameters
): Worker(appContext, workerParams) {

    override fun doWork(): Result {
        sendNotification()
        return Result.success()
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun sendNotification(){
        val id = Constants.NOTIFICATION_ID_VALUE

        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(Constants.NOTIFICATION_ID, id)

        val notificationManager: NotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val bitmap: Bitmap = AppCompatResources
            .getDrawable(applicationContext, R.drawable.ic_dish_launcher_long)!!
            .toBitmap()
        val title = applicationContext.getString(R.string.notification_title)
        val text = applicationContext.getString(R.string.notification_text)

        val pendingIntent =
            PendingIntent.getActivity(applicationContext, 0, intent, 0)

        val notification =
            NotificationCompat.Builder(applicationContext, Constants.NOTIFICATION_CHANNEL)
                .setSmallIcon(R.drawable.ic_dish_launcher)
                .setColor(ContextCompat.getColor(applicationContext, R.color.primaryDarkColor))
                .setLargeIcon(bitmap)
                .setContentTitle(title)
                .setContentText(text)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(pendingIntent)
                .setStyle(
                    NotificationCompat.BigPictureStyle()
                        .bigPicture(bitmap)
                        .bigLargeIcon(null))
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_LOW)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notification.setChannelId(Constants.NOTIFICATION_CHANNEL)

            val ringtoneManager = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val audioAttributes =
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()

            val channel = NotificationChannel(
                Constants.NOTIFICATION_CHANNEL,
                Constants.NOTIFICATION_NAME,
                NotificationManager.IMPORTANCE_LOW)

            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            channel.setSound(ringtoneManager, audioAttributes)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(id, notification.build())
    }
}
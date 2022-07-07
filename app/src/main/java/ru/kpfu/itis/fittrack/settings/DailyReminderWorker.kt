package ru.kpfu.itis.fittrack.settings

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.kpfu.itis.fittrack.MainActivity
import ru.kpfu.itis.fittrack.R

class DailyReminderWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        // создаем уведомление
        createNotification()
        return Result.success()
    }

    private fun createNotification() {
        val intent = Intent(
            applicationContext,
            MainActivity::class.java
        ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(
            applicationContext,
            SettingsFragment.NOTIFICATIONS_CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle(applicationContext.resources.getString(R.string.notif_reminder))
            .setContentText(applicationContext.resources.getString(R.string.notif_reminder_content))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(applicationContext)) {
            // notificationId is a unique int for each notification that you must define
            val notificationId: Int = (0..100).random()
            notify(notificationId, builder.build())
        }
    }

}

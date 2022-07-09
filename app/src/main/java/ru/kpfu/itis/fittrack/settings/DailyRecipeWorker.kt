package ru.kpfu.itis.fittrack.settings

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.settings.NotificationHelper.Companion.NOTIFICATIONS_CHANNEL_ID

class DailyRecipeWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    private val context = appContext

    override fun doWork(): Result {
        // создаем уведомление
        createNotification()
        return Result.success()
    }

    private fun createNotification() {
        val builder = NotificationCompat.Builder(
            applicationContext,
            NOTIFICATIONS_CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_baseline_menu_book_24)
            .setContentTitle(context.resources.getString(R.string.notif_daily_recipe))
            // ПОЛУЧАЕМ РЕЦЕПТ ИЗ БАЗЫ ДАННЫХ и сеттим его в контент текст: смогу дописать, когда будет бд
            .setContentText(context.resources.getString(R.string.notif_reminder_content))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val manager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(1, builder.build())

    }
}

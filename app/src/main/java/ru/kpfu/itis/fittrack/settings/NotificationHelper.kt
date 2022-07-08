package ru.kpfu.itis.fittrack.settings

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import androidx.preference.PreferenceManager
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import ru.kpfu.itis.fittrack.R
import java.util.*
import java.util.concurrent.TimeUnit

class NotificationHelper(private val context: Context) {

    companion object {
        const val NOTIFICATIONS_CHANNEL_ID = "id-notifications-channel"
        const val SP_KEY_IS_CHANNEL_CREATED = "isChannelCreated"
        const val SP_KEY_WORKREQUEST_REMINDER_ID = "wr-reminder"
        const val SP_KEY_WORKREQUEST_RECIPE_ID = "wr-recipe"
        const val SEC_IN_HOUR = 3600
        const val SEC_IN_MINUTE = 60
        const val SEC_IN_DAY = 86400
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.resources.getString(R.string.notif_channel_name)
            val descriptionText = context.resources.getString(R.string.notif_channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(
                NOTIFICATIONS_CHANNEL_ID,
                name,
                importance
            ).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    private fun createChannelIfItIsNotCreated() {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        if (!sp.getBoolean(SP_KEY_IS_CHANNEL_CREATED, false)) {
            createNotificationChannel()
            sp.edit().putBoolean(SP_KEY_IS_CHANNEL_CREATED, true).apply()
        }
    }

    fun createNotification(flag: String, keyToSaving: String = "") {
        // 0) создаем канал для уведомлений, если он еще не создан
        // 1) открываем диалоговое окно с выбором времени
        // 2) получаем оттуда время и запускаем WorkRequest
        createChannelIfItIsNotCreated()
        showDialogTimePicker(flag, keyToSaving)
    }

    fun cancelWorkRequest(key: String, keyToSaving: String = "") {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        if (sp.contains(key)) {
            val id = UUID.fromString(sp.getString(key, ""))
            WorkManager.getInstance(context).cancelWorkById(id)
        }
        if (keyToSaving.isNotEmpty()) {
            saveSwitchState(key, false)
        }
    }

    private fun startWorkRequest(hours: Int, minute: Int, flag: String, keyToSaving: String = "") {
        val calendar = Calendar.getInstance()
        var delayInSeconds =
            (hours - calendar.get(Calendar.HOUR_OF_DAY)) * SEC_IN_HOUR + (minute - calendar.get(
                Calendar.MINUTE
            )) * SEC_IN_MINUTE - calendar.get(Calendar.SECOND)

        if (delayInSeconds < 0) {
            delayInSeconds += SEC_IN_DAY
        }

        var workRequest: WorkRequest? = null
        when (flag) {
            SP_KEY_WORKREQUEST_RECIPE_ID ->
                workRequest = PeriodicWorkRequestBuilder<DailyRecipeWorker>(1, TimeUnit.DAYS)
                    .setInitialDelay(delayInSeconds.toLong(), TimeUnit.SECONDS)
                    .build()
            SP_KEY_WORKREQUEST_REMINDER_ID -> workRequest =
                PeriodicWorkRequestBuilder<DailyReminderWorker>(1, TimeUnit.DAYS)
                    .setInitialDelay(delayInSeconds.toLong(), TimeUnit.SECONDS)
                    .build()

        }
        WorkManager.getInstance(context).enqueue(workRequest!!)

        // нужно запоминать айди работающего workRequest_a, чтобы его отменять
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(
            flag, workRequest.id.toString()
        ).apply()

        if (keyToSaving.isNotEmpty()) {
            saveSwitchState(keyToSaving, true)
        }

    }

    private fun showDialogTimePicker(flag: String, keyToSaving: String = "") {
        // listener to perform task
        // when time is picked
        val timePickerDialogListener: TimePickerDialog.OnTimeSetListener =
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                startWorkRequest(hourOfDay, minute, flag, keyToSaving)
            }
        TimePickerDialog(
            context,
            timePickerDialogListener,
            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
            Calendar.getInstance().get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun saveSwitchState(key: String, value: Boolean) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putBoolean(key, value).apply()
    }
}

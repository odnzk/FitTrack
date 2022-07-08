package ru.kpfu.itis.fittrack.settings

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import ru.kpfu.itis.fittrack.R
import java.util.*
import java.util.concurrent.TimeUnit

class SettingsFragment : PreferenceFragmentCompat() {

    companion object ImportantConstants {
        const val NOTIFICATIONS_CHANNEL_ID = "id-notifications-channel"
        const val SP_KEY_IS_CHANNEL_CREATED = "isChannelCreated"
        const val SP_KEY_WORKREQUEST_REMINDER_ID = "wr-reminder"
        const val SP_KEY_WORKREQUEST_RECIPE_ID = "wr-recipe"
        const val SEC_IN_HOUR = 3600
        const val SEC_IN_MINUTE = 60
        const val SEC_IN_DAY = 86400
        const val MIN_VALUE = 1
        const val MAX_VALUE = 250
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        findPreference<EditTextPreference>("et_height")
            ?.setOnPreferenceChangeListener { _, newValue ->
                isValid(newValue.toString())
            }

        findPreference<EditTextPreference>("et_weight")
            ?.setOnPreferenceChangeListener { _, newValue ->
                isValid(newValue.toString())
            }

        findPreference<EditTextPreference>("et_age")
            ?.setOnPreferenceChangeListener { _, newValue ->
                isValid(newValue.toString())
            }


        findPreference<SwitchPreferenceCompat>("switchDailyRecipe")
            ?.setOnPreferenceChangeListener { _, newValue ->
                if (newValue as Boolean) {
                    createNotification(SP_KEY_WORKREQUEST_RECIPE_ID)
                } else {
                    cancelWorkRequest(SP_KEY_WORKREQUEST_RECIPE_ID)
                }
                true
            }

        findPreference<SwitchPreferenceCompat>("switchReminder")
            ?.setOnPreferenceChangeListener { _, newValue ->
                if (newValue as Boolean) {
                    createNotification(SP_KEY_WORKREQUEST_REMINDER_ID)
                } else {
                    cancelWorkRequest(SP_KEY_WORKREQUEST_REMINDER_ID)
                }
                true
            }
    }

    private fun showDialogTimePicker(flag: String) {
        // listener to perform task
        // when time is picked
        val timePickerDialogListener: TimePickerDialog.OnTimeSetListener =
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                startWorkRequest(hourOfDay, minute, flag)
            }
        TimePickerDialog(
            requireContext(),
            timePickerDialogListener,
            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
            Calendar.getInstance().get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun startWorkRequest(hours: Int, minute: Int, flag: String) {
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
        WorkManager.getInstance(requireContext()).enqueue(workRequest!!)

        // нужно запоминать айди работающего workRequest_a, чтобы его отменять
        PreferenceManager.getDefaultSharedPreferences(requireContext()).edit().putString(
            flag, workRequest.id.toString()
        ).apply()

    }

    private fun cancelWorkRequest(key: String) {
        val sp = PreferenceManager.getDefaultSharedPreferences(requireContext())
        if (sp.contains(key)) {
            val id = UUID.fromString(sp.getString(key, ""))
            WorkManager.getInstance(requireContext()).cancelWorkById(id)
        }
    }


    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = resources.getString(R.string.notif_channel_name)
            val descriptionText = resources.getString(R.string.notif_channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(NOTIFICATIONS_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createChannelIfItIsNotCreated() {
        val sp = PreferenceManager.getDefaultSharedPreferences(requireContext())
        if (!sp.getBoolean(SP_KEY_IS_CHANNEL_CREATED, false)) {
            createNotificationChannel()
            sp.edit().putBoolean(SP_KEY_IS_CHANNEL_CREATED, true).apply()
        }
    }

    private fun createNotification(flag: String) {
        // 0) создаем канал для уведомлений, если он еще не создан
        // 1) открываем диалоговое окно с выбором времени
        // 2) получаем оттуда время и запускаем WorkRequest
        createChannelIfItIsNotCreated()
        showDialogTimePicker(flag)
    }

    private fun isValid(newValue: String):Boolean = newValue.isNotEmpty() && newValue.toInt() in MIN_VALUE..MAX_VALUE

}

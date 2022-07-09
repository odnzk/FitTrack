package ru.kpfu.itis.fittrack.settings

import android.os.Bundle
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import ru.kpfu.itis.fittrack.R

class SettingsFragment : PreferenceFragmentCompat() {
    companion object {
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
                    NotificationHelper(requireContext()).createNotification(NotificationHelper.SP_KEY_WORKREQUEST_RECIPE_ID)
                } else {
                    NotificationHelper(requireContext()).cancelWorkRequest(NotificationHelper.SP_KEY_WORKREQUEST_RECIPE_ID)
                }
                true
            }

        findPreference<SwitchPreferenceCompat>("switchReminder")
            ?.setOnPreferenceChangeListener { _, newValue ->
                if (newValue as Boolean) {
                    NotificationHelper(requireContext()).createNotification(NotificationHelper.SP_KEY_WORKREQUEST_REMINDER_ID)
                } else {
                    NotificationHelper(requireContext()).cancelWorkRequest(NotificationHelper.SP_KEY_WORKREQUEST_REMINDER_ID)
                }
                true
            }
    }

    private fun isValid(newValue: String): Boolean =
        newValue.isNotEmpty() && newValue.toInt() in MIN_VALUE..MAX_VALUE

}

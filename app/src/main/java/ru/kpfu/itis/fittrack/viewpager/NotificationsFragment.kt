package ru.kpfu.itis.fittrack.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import ru.kpfu.itis.fittrack.databinding.FragmentNotificationsBinding
import ru.kpfu.itis.fittrack.settings.NotificationHelper

class NotificationsFragment : Fragment() {
    private var _binding: FragmentNotificationsBinding? = null
    private val binging get() = _binding!!

    companion object{
        const val SP_KEY_SWITCH_RECIPE = "switchDailyRecipe"
        const val SP_KEY_SWITCH_REMINDER = "switchReminder"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)

        with(binging){
            btnOnRecipe.setOnClickListener {
                NotificationHelper(requireContext()).createNotification(NotificationHelper.SP_KEY_WORKREQUEST_RECIPE_ID, SP_KEY_SWITCH_RECIPE)
            }
            btnOffRecipe.setOnClickListener{
                NotificationHelper(requireContext()).cancelWorkRequest(NotificationHelper.SP_KEY_WORKREQUEST_RECIPE_ID, SP_KEY_SWITCH_RECIPE)
            }
            btnOnReminder.setOnClickListener {
                NotificationHelper(requireContext()).createNotification(NotificationHelper.SP_KEY_WORKREQUEST_REMINDER_ID, SP_KEY_SWITCH_REMINDER)
            }
            btnOffReminder.setOnClickListener{
                NotificationHelper(requireContext()).cancelWorkRequest(NotificationHelper.SP_KEY_WORKREQUEST_REMINDER_ID, SP_KEY_SWITCH_REMINDER)
            }
        }
        return binging.root
    }


}

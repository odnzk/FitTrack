package ru.kpfu.itis.fittrack.listForTheDay

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.kpfu.itis.fittrack.fragments.ProductDescriptionFragment

class StatWorker(
    appContext: Context,
    workerParameters: WorkerParameters,
) : Worker(appContext, workerParameters) {

    private val context = appContext
    private val list = MutableList(7) { 0 }

    override fun doWork(): Result {
        val sharedPreferences = context.getSharedPreferences(
            "UserData",
            Context.MODE_PRIVATE,
        )
        saveSharedPref(sharedPreferences)
        clearSharedPref(sharedPreferences)
        return Result.success()
    }

    private fun clearSharedPref(sharedPreferences: SharedPreferences) {
        val editor = sharedPreferences.edit()
        with(editor) {
            putBoolean(ProductDescriptionFragment.CLEAR_LIST,true).apply()
            putInt(ProductDescriptionFragment.BURNED_CALORIES, 0).apply()
            putInt(ProductDescriptionFragment.EATEN_CALORIES, 0).apply()
            putFloat(ProductDescriptionFragment.EATEN_PROTEINS, 0f).apply()
            putFloat(ProductDescriptionFragment.EATEN_CARBS, 0f).apply()
            putFloat(ProductDescriptionFragment.EATEN_FATS, 0f).apply()
        }
    }

    private fun saveSharedPref(sharedPreferences: SharedPreferences) {
        for (i in 0 .. 6){
            list[i] = sharedPreferences.getInt("$SAVED_DAY$i",0)
        }
        for (i in 0 until 6) {
            list[i] = list[i + 1]
        }
        list[list.lastIndex] =
            sharedPreferences.getInt(ProductDescriptionFragment.EATEN_CALORIES, 0)
        val editor = sharedPreferences.edit()
        for (i in 0..6) {
            editor.putInt("$SAVED_DAY$i", list[i]).apply()
        }
    }

    companion object {
        const val SAVED_DAY = "save day:"
    }
}
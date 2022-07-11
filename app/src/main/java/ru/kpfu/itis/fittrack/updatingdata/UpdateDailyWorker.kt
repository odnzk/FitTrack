package ru.kpfu.itis.fittrack.updatingdata

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.kpfu.itis.fittrack.fragments.ProductDescriptionFragment
import ru.kpfu.itis.fittrack.listForTheDay.SharedPreferencesStorage
import ru.kpfu.itis.fittrack.recipesFromAPI.RandomRecipeGenerator
import ru.kpfu.itis.fittrack.statsdata.EntireDatabase
import ru.kpfu.itis.fittrack.statsdata.StatsItem
import java.util.*

class UpdateDailyWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    private val context = appContext
    override fun doWork(): Result {
        saveDataToDatabase()
        clearListForTheDay()
        getRandomRecipe()
        return Result.success()
    }

    private fun getRandomRecipe() {
        RandomRecipeGenerator().getRandomRecipe {
            val runnable = {
                EntireDatabase.getDatabase(context).recipeDao().add(it)
            }
            Thread(runnable).start()
        }
    }

    private fun saveDataToDatabase() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        val sp = context.getSharedPreferences(
            "UserData",
            Context.MODE_PRIVATE,
        )
        val calories = sp.getInt(ProductDescriptionFragment.EATEN_CALORIES, 0) - sp.getInt(
            ProductDescriptionFragment.BURNED_CALORIES,
            0
        )
        val proteins = sp.getFloat(ProductDescriptionFragment.EATEN_PROTEINS, 0f)
        val carbs = sp.getFloat(ProductDescriptionFragment.EATEN_CARBS, 0f)
        val fats = sp.getFloat(ProductDescriptionFragment.EATEN_FATS, 0f)
        val statsItem = StatsItem(
            calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.YEAR),
            calories,
            proteins,
            fats,
            carbs
        )
        val runnable = {
            EntireDatabase.getDatabase(context).statsItemDao().insert(statsItem)
        }
        Thread(runnable).start()
    }

    private fun clearListForTheDay() {
        SharedPreferencesStorage(context).clearAll()
    }

}

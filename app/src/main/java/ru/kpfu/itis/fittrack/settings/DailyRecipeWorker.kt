package ru.kpfu.itis.fittrack.settings

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.data.RecipeDatabase
import ru.kpfu.itis.fittrack.settings.NotificationHelper.Companion.NOTIFICATIONS_CHANNEL_ID
import java.util.concurrent.FutureTask

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
            .setContentText(getRandomRecipeFromDataBase())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val manager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(1, builder.build())

    }

    private fun getRandomRecipeFromDataBase(): String {
        val callable = {
            val dao = RecipeDatabase.getDatabase(context).recipeDao()
            val countRecipes = dao.countRecipes()
            if (countRecipes == 0) {
                context.resources.getString(R.string.no_recipes)
            } else {
                var randomIndex = (1..countRecipes).random()
                var recipe = dao.getRecipeById(randomIndex)
                while (recipe == null) {
                    randomIndex = (1..countRecipes).random()
                    recipe = dao.getRecipeById(randomIndex)
                }
                recipe.title
            }
        }
        val future = FutureTask(callable)
        Thread(future).start()
        return future.get().toString()
    }
}

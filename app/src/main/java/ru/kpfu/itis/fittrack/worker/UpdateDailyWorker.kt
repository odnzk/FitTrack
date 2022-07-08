package ru.kpfu.itis.fittrack.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.kpfu.itis.fittrack.listForTheDay.SharedPreferencesStorage

class UpdateDailyWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        saveInfoForStats()
        cleaList()
        return Result.success()
    }
    private fun saveInfoForStats(){

    }

    private fun cleaList(){
        SharedPreferencesStorage(applicationContext).clearAll()
    }
}

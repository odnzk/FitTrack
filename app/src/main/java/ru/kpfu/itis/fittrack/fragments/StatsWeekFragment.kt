package ru.kpfu.itis.fittrack.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.dataForStats.StatsItem
import ru.kpfu.itis.fittrack.dataForStats.StatsItemDatabase
import java.util.concurrent.Future
import java.util.concurrent.FutureTask

class StatsWeekFragment : Fragment(R.layout.fragment_stats_week) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // просто вызывай этот метод, там все данные с бд
//        val listWithStats = getAllData()
//        Log.d("HELPME", listWithStats.first().toString())
//        Log.d("HELPME", listWithStats.size.toString())
    }

//    private fun getAllData():List<StatsItem>{
//        // конечно можно лучше, но я не особо
//        val callableStatsList = {
//            StatsItemDatabase.getDatabase(requireContext()).statsItemDao().insertNewStatsItem(
//                StatsItem(8, 7, 2022, 2539, 99, 99, 99)
//            )
//            StatsItemDatabase.getDatabase(requireContext()).statsItemDao().getAllStatsItems()
//        }
//        val futureTask =  FutureTask(callableStatsList)
//        Thread(futureTask).start()
//        return futureTask.get()
//    }
}

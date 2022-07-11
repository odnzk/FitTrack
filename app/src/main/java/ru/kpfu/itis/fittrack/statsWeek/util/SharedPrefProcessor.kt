package ru.kpfu.itis.fittrack.statsWeek.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import ru.kpfu.itis.fittrack.databinding.FragmentStatsWeekBinding

class SharedPrefProcessor(private val pref: SharedPreferences) {

    fun getInitGraphLists(): Pair<List<Float>, List<String>> {
        val listData = ArrayList<Float>()
        val listInfo = ArrayList<String>()
        val count = pref.getInt("STATS_WEEK_DATA_COUNT", 0)
        for (i in 0 until count) {
            listInfo.add(pref.getString("STATS_WEEK_INFO_${i + 1}", "") ?: "")
            listData.add(pref.getFloat("STATS_WEEK_DATA_${i + 1}", 0f))
        }
        return listData to listInfo
    }

    fun saveOnPref(
        listData: List<Float>,
        listInfo: List<String>,
        spinnerTopItem: String,
        spinnerDownItem: String,
    ) {
        pref.edit {
            for (i in listData.indices) {
                putFloat("STATS_WEEK_DATA_${i + 1}", listData[i])
                putString("STATS_WEEK_INFO_${i + 1}", listInfo[i])
            }
            putInt("STATS_WEEK_DATA_COUNT", listData.size)
            putString("STATS_WEEK_SPINNER_TOP_ITEM", spinnerTopItem)
            putString("STATS_WEEK_SPINNER_DOWN_ITEM", spinnerDownItem)
        }
    }

    fun getSpinnerIndexes(dateList: List<String>): Pair<Int, Int> =
        dateList.indexOf(
            pref.getString(
                "STATS_WEEK_SPINNER_TOP_ITEM",
                dateList[0],
            )
        ) to dateList.indexOf(
            pref.getString("STATS_WEEK_SPINNER_DOWN_ITEM",
                dateList[0]
            )
        )
}

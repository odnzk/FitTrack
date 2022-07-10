package ru.kpfu.itis.fittrack.util

import ru.kpfu.itis.fittrack.statsdata.StatsItem

class DateProcessor {
    fun getCurrentIndexesFromDateBorders(
        left: String,
        right: String,
        data: List<StatsItem>,
    ): Pair<Int, Int> {
        val list = getDatesFromDBList(data)
        var indexLeft = 0
        var indexRight = 0
        for (i in list.indices)
            if (list[i] == left) {
                indexLeft = i
            }
        for (i in indexLeft until list.size) {
            if (list[i] == right) {
                indexRight = i
            }
        }
        return Pair(indexLeft, indexRight)
    }

    fun getDatesFromDBList(list: List<StatsItem>): List<String> {
        val datesList = ArrayList<String>()
        list.forEach {
            datesList.add("${it.day}-${it.month}-${it.year}")
        }
        return datesList
    }
}

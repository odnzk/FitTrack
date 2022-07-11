package ru.kpfu.itis.fittrack.statsWeek.util

import ru.kpfu.itis.fittrack.statsdata.StatsItem

class DBListProcessor(private val data: List<StatsItem>) {
    fun getGraphListsFromDB(
        leftDate: String,
        rightDate: String,
    ): Pair<List<Float>, List<String>> {
        val pairIndexes = getCurrentIndexesFromDateBorders(leftDate, rightDate)
        val left = pairIndexes.first
        val right = pairIndexes.second
        val list = filterList(left, right)
        val dataList = ArrayList<Float>()
        val infoList = ArrayList<String>()
        list.forEach {
            dataList.add(it.consumedCalories.toFloat())
            infoList.add("${it.day}-${it.month}-${it.year}")
        }
        return dataList to infoList
    }

    fun getDatesFromDBList(): List<String> {
        val datesList = ArrayList<String>()
        data.forEach {
            datesList.add("${it.day}-${it.month}-${it.year}")
        }
        return datesList
    }

    private fun getCurrentIndexesFromDateBorders(
        left: String,
        right: String,
    ): Pair<Int, Int> {
        val list = getDatesFromDBList()
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
        return indexLeft to indexRight
    }

    private fun filterList(left: Int, right: Int): List<StatsItem> {
        val list = ArrayList<StatsItem>()
        for (i in left..right) {
            list.add(data[i])
        }
        return list
    }
}

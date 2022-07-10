package ru.kpfu.itis.fittrack.util

import ru.kpfu.itis.fittrack.statsdata.StatsItem

fun filterList(left: Int, right: Int, data: List<StatsItem>): List<StatsItem> {
    val list = ArrayList<StatsItem>()
    for (i in left..right) {
        list.add(data[i])
    }
    return list
}

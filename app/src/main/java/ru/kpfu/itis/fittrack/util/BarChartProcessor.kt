package ru.kpfu.itis.fittrack.util

import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class BarChartProcessor(barChart: BarChart) {
    private val chart = barChart

    fun setGraphDataFromList(list: List<Float>, label: String) {
        chart.data = BarData(
            createDataSetFromList(list, label)
        )
    }

    fun setStringFields(list: List<String>) {
        chart.xAxis.valueFormatter = IndexAxisValueFormatter(list)
    }

    private fun createDataSetFromList(list: List<Float>, label: String): BarDataSet {
        val entryList = ArrayList<BarEntry>()
        var i = 0f
        list.forEach {
            entryList.add(
                BarEntry(i++, it)
            )
        }
        return BarDataSet(entryList, label)
    }
}
package ru.kpfu.itis.fittrack.util

import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class BarChartProcessor(barChart: BarChart) {
    private val chart = barChart
    private val label = "Calories"

    init {
        chart.getAxis(YAxis.AxisDependency.LEFT).axisMinimum = 0f
        chart.xAxis.setDrawGridLines(false)
        chart.axisLeft.setDrawGridLines(false)
        chart.axisRight.setDrawGridLines(false)
    }

    fun setGraphDataFromList(list: List<Float>) {
        chart.data = BarData(
            createDataSetFromList(list)
        )
    }

    fun setStringFields(list: List<String>) {
        chart.xAxis.valueFormatter = IndexAxisValueFormatter(list)
    }

    fun addData(item: Float, info: String) {
        val listData = ArrayList<Float>()
        val listInfo = ArrayList<String>()
        val dataSet = chart.data.dataSets[0]
        dataSet.apply {
            for (i in 1 until entryCount) {
                val entry = getEntryForIndex(i)
                listData.add(entry.y)
                listInfo.add(
                    chart
                        .xAxis
                        .valueFormatter
                        .getFormattedValue(entry.x)
                )
            }
        }
        listData.add(item)
        listInfo.add(info)
        setGraphDataFromList(listData)
        setStringFields(listInfo)
    }

    private fun createDataSetFromList(list: List<Float>): BarDataSet {
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

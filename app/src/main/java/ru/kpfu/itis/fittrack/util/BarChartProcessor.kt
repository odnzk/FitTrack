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
    private var index = 0

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

    fun setStringFieldsFromList(list: List<String>) {
        chart.xAxis.valueFormatter = IndexAxisValueFormatter(list)
    }

    fun add(item: Float, info: String) {
        if (index < 7) {
            addNotFilled(item, info)
        } else {
            addFilled(item, info)
        }
    }

    private fun addNotFilled(item: Float, info: String) {
        val listInfo = ArrayList<String>()
        val listData = ArrayList<Float>()
        val dataSet = chart.data.dataSets[0]
        val count = chart.data.dataSets[0].entryCount
        val formatter = chart.xAxis.valueFormatter
        for (i in 0 until index) {
            listInfo.add(formatter.getFormattedValue(i.toFloat()))
            listData.add(dataSet.getEntryForIndex(i).y)
        }
        listInfo.add(info)
        listData.add(item)
        for (i in index + 1 until count) {
            listInfo.add("")
            listData.add(0f)
        }
        setStringFieldsFromList(listInfo)
        setGraphDataFromList(listData)
        index++
    }

    private fun addFilled(item: Float, info: String) {
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
        setStringFieldsFromList(listInfo)
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

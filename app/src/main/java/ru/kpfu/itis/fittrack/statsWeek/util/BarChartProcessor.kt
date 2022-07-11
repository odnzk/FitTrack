package ru.kpfu.itis.fittrack.statsWeek.util

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
        with(chart) {
            getAxis(YAxis.AxisDependency.LEFT).axisMinimum = 0f
            xAxis.setDrawGridLines(false)
            axisLeft.setDrawGridLines(false)
            axisRight.setDrawGridLines(false)
            setScaleEnabled(false)
            description.text = ""
        }
    }

    fun setGraphLists(listData: List<Float>, listInfo: List<String>) {
        setGraphDataFromList(listData)
        setGraphInfoFromList(listInfo)
        chart.resetZoom()
    }

    fun getDataList(): List<Float> {
        val list = ArrayList<Float>()
        val dataSet = chart.data.dataSets[0]
        for (i in 0 until dataSet.entryCount) {
            list.add(dataSet.getEntryForIndex(i).y)
        }
        return list
    }

    fun getInfoList(): List<String> {
        val list = ArrayList<String>()
        for (i in 0 until chart.data.dataSets[0].entryCount) {
            list.add(
                chart
                    .xAxis
                    .valueFormatter
                    .getFormattedValue(i.toFloat())
            )
        }
        return list
    }

    private fun setGraphDataFromList(list: List<Float>) {
        chart.data = BarData(
            createDataSetFromList(list)
        )
    }

    private fun setGraphInfoFromList(list: List<String>) {
        chart.xAxis.valueFormatter = IndexAxisValueFormatter(list)
        chart.xAxis.labelCount = list.size
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

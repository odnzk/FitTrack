package ru.kpfu.itis.fittrack.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.databinding.FragmentStatsWeekBinding

class StatsWeekFragment : Fragment(R.layout.fragment_stats_week) {

    private var _binding: FragmentStatsWeekBinding? = null
    private val binding get() = _binding!!

    var graphLabel: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStatsWeekBinding.bind(view)

        graphLabel = "Calories"

        setGraphData(arrayListOf(1f, 2f, 3f, 4f, 5f, 6f, 7f), graphLabel)

    }

    private fun setGraphData(list: List<Float>, label: String) {
        with(binding) {
            barGraph.data = BarData(
                createDataSetFromList(list, label)
            )
        }
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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}

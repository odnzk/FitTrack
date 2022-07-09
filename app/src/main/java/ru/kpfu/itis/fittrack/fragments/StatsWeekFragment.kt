package ru.kpfu.itis.fittrack.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.databinding.FragmentStatsWeekBinding
import ru.kpfu.itis.fittrack.util.BarChartProcessor


class StatsWeekFragment : Fragment(R.layout.fragment_stats_week) {

    private var _binding: FragmentStatsWeekBinding? = null
    private val binding get() = _binding!!

    private lateinit var processor: BarChartProcessor
    private lateinit var pref: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStatsWeekBinding.bind(view)
        processor = BarChartProcessor(binding.barGraph)
        with(binding.barGraph) {
            setScaleEnabled(false)
            description.text = ""
        }

        initSharedPref()
        init()

        binding.button.setOnClickListener {
            processor.add(getDataFromPref(), getCurrentDate())
            saveOnPref()
        }

    }

    private fun initSharedPref() {
        pref = activity?.getSharedPreferences("TEST", Context.MODE_PRIVATE)!!
    }

    private fun saveOnPref() {
        val listData = processor.getDataList()
        val listInfo = processor.getInfoList()
        pref.edit {
            for (i in 0..6) {
                putFloat("STATS_WEEK_DATA_${i + 1}", listData[i])
                putString("STATS_WEEK_INFO_${i + 1}", listInfo[i])
            }
            putInt("STATS_WEEK_INDEX", processor.index)
        }

    }

    private fun getCurrentDate(): String = "11.06.22" // mock for test

    private fun getDataFromPref(): Float = 10f // mock for test

    private fun init() {
        val listData = ArrayList<Float>()
        val listInfo = ArrayList<String>()
        for (i in 0..6) {
            listInfo.add(pref.getString("STATS_WEEK_INFO_${i + 1}", "") ?: "")
            listData.add(pref.getFloat("STATS_WEEK_DATA_${i + 1}", 0f))
        }
        processor.index = pref.getInt("STATS_WEEK_INDEX", 0)
        processor.setGraphLists(listData, listInfo)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}

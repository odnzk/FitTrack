package ru.kpfu.itis.fittrack.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.databinding.FragmentStatsWeekBinding
import ru.kpfu.itis.fittrack.util.BarChartProcessor
import java.util.*


class StatsWeekFragment : Fragment(R.layout.fragment_stats_week) {

    private var _binding: FragmentStatsWeekBinding? = null
    private val binding get() = _binding!!

    private lateinit var processor: BarChartProcessor
    private lateinit var pref: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.O)
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentDate(): String {
        val date = Date().toString().split(" ")
        val yesterday = Calendar.getInstance()
        yesterday.add(Calendar.DATE, -1)
        return Date.from(yesterday.toInstant()).toString().split(" ").let {
            it[1] + " " + it[2]
        }
    }

    private fun getDataFromPref(): Float {
        return pref.getInt("save day:6", 0).toFloat()
    }

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

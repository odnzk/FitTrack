package ru.kpfu.itis.fittrack.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.databinding.FragmentStatsWeekBinding
import ru.kpfu.itis.fittrack.statsdata.StatsViewModel
import ru.kpfu.itis.fittrack.util.BarChartProcessor
import ru.kpfu.itis.fittrack.util.DateProcessor


class StatsWeekFragment : Fragment(R.layout.fragment_stats_week) {

    private var _binding: FragmentStatsWeekBinding? = null
    private val binding get() = _binding!!
    private lateinit var vm: StatsViewModel

    private lateinit var chartProcessor: BarChartProcessor
    private lateinit var dateProcessor: DateProcessor
    private lateinit var pref: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStatsWeekBinding.bind(view)
        chartProcessor = BarChartProcessor(binding.barGraph)
        dateProcessor = DateProcessor()
        with(binding.barGraph) {
            setScaleEnabled(false)
            description.text = ""
        }

        initSharedPref()
        init()

        vm = ViewModelProvider(this)[StatsViewModel::class.java]
        vm.getAllStats.observe(viewLifecycleOwner) {
            val dateList = dateProcessor.getDatesFromDBList(it)
            with(binding) {
                setSpinnerData(
                    spinnerTop,
                    dateList,
                    dateList.indexOf(
                        pref.getString(
                            "STATS_WEEK_SPINNER_TOP_ITEM",
                            dateList[0],
                        )
                    )
                )
                setSpinnerData(
                    spinnerDown,
                    dateList,
                    dateList.indexOf(
                        pref.getString(
                            "STATS_WEEK_SPINNER_DOWN_ITEM",
                            dateList[0],
                        )
                    )
                )
                btnShow.setOnClickListener { _ ->
                    val left = spinnerTop.selectedItem.toString()
                    val right = spinnerDown.selectedItem.toString()
                    val pairIndexes = dateProcessor.getCurrentIndexesFromDateBorders(
                        left,
                        right,
                        it
                    )
                    chartProcessor.setGraphListsFromDB(
                        pairIndexes.first,
                        pairIndexes.second,
                        it
                    )
                }
            }
        }
    }

    private fun setSpinnerData(spinner: Spinner, data: List<String>, index: Int) {
        context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                data,
            )
        }?.also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.setSelection(index)
    }

    private fun initSharedPref() {
        pref = activity?.getSharedPreferences("STATS_WEEK", Context.MODE_PRIVATE)!!
    }

    private fun saveOnPref() {
        val listData = chartProcessor.getDataList()
        val listInfo = chartProcessor.getInfoList()
        pref.edit {
            for (i in listData.indices) {
                putFloat("STATS_WEEK_DATA_${i + 1}", listData[i])
                putString("STATS_WEEK_INFO_${i + 1}", listInfo[i])
            }
            putInt("STATS_WEEK_DATA_COUNT", listData.size)
            putString("STATS_WEEK_SPINNER_TOP_ITEM", binding.spinnerTop.selectedItem.toString())
            putString("STATS_WEEK_SPINNER_DOWN_ITEM", binding.spinnerDown.selectedItem.toString())
        }

    }

    private fun init() {
        val listData = ArrayList<Float>()
        val listInfo = ArrayList<String>()
        val count = pref.getInt("STATS_WEEK_DATA_COUNT", 0)
        for (i in 0 until count) {
            listInfo.add(pref.getString("STATS_WEEK_INFO_${i + 1}", "") ?: "")
            listData.add(pref.getFloat("STATS_WEEK_DATA_${i + 1}", 0f))
        }
        chartProcessor.setGraphLists(listData, listInfo)
    }

    override fun onDestroyView() {
        saveOnPref()
        _binding = null
        super.onDestroyView()
    }
}

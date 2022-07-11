package ru.kpfu.itis.fittrack.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.databinding.FragmentStatsWeekBinding
import ru.kpfu.itis.fittrack.statsWeek.util.*
import ru.kpfu.itis.fittrack.statsdata.StatsViewModel


class StatsWeekFragment : Fragment(R.layout.fragment_stats_week) {

    private var _binding: FragmentStatsWeekBinding? = null
    val binding get() = _binding!!
    private lateinit var vm: StatsViewModel

    private lateinit var chartProcessor: BarChartProcessor
    private lateinit var sharedPrefProcessor: SharedPrefProcessor
    private lateinit var pref: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStatsWeekBinding.bind(view)
        init()

        vm = ViewModelProvider(this)[StatsViewModel::class.java]
        vm.getAllStats.observe(viewLifecycleOwner) {
            val dbListProcessor = DBListProcessor(it)
            val dateList = dbListProcessor.getDatesFromDBList()
            with(binding) {
                SpinnerProcessor(requireContext()).processSpinners(
                    spinnerTop,
                    spinnerDown,
                    dateList,
                    sharedPrefProcessor.getSpinnerIndexes(dateList)
                )
                btnShow.setOnClickListener {
                    val graphListsPair = dbListProcessor.getGraphListsFromDB(
                        spinnerTop.selectedItem.toString(),
                        spinnerDown.selectedItem.toString()
                    )
                    chartProcessor.setGraphLists(
                        graphListsPair.first,
                        graphListsPair.second
                    )
                    sharedPrefProcessor.saveOnPref(
                        chartProcessor.getDataList(),
                        chartProcessor.getInfoList(),
                        binding.spinnerTop.selectedItem.toString(),
                        binding.spinnerDown.selectedItem.toString(),
                    )
                }
            }
        }
    }

    private fun init() {
        chartProcessor = BarChartProcessor(binding.barGraph)
        pref = activity?.getSharedPreferences("STATS_WEEK", Context.MODE_PRIVATE)!!
        sharedPrefProcessor = SharedPrefProcessor(pref)

        val initLists = sharedPrefProcessor.getInitGraphLists()
        chartProcessor.setGraphLists(initLists.first, initLists.second)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}

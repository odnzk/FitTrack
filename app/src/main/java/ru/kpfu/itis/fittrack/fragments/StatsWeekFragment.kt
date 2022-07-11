package ru.kpfu.itis.fittrack.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.databinding.FragmentStatsWeekBinding
import ru.kpfu.itis.fittrack.statsdata.StatsViewModel
import ru.kpfu.itis.fittrack.util.BarChartProcessor


class StatsWeekFragment : Fragment(R.layout.fragment_stats_week) {

    private var _binding: FragmentStatsWeekBinding? = null
    private val binding get() = _binding!!
    private lateinit var vm: StatsViewModel

    private lateinit var processor: BarChartProcessor

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStatsWeekBinding.bind(view)
        processor = BarChartProcessor(binding.barGraph)
        with(binding.barGraph) {
            setScaleEnabled(false)
            description.text = ""
        }

        val graphLabel = "Calories"
        val data = arrayListOf(
            1f,
            2f,
            3f,
            4f,
            5f,
            6f,
            7f
        )
        val strings = arrayListOf(
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday",
            "Sunday"
        )

        with(processor) {
            setGraphDataFromList(data, graphLabel)
            setStringFields(strings)
        }

        /////////////
        vm = ViewModelProvider(this)[StatsViewModel::class.java]
        vm.getAllStats.observe(viewLifecycleOwner) {
            // ровно семь штук залила, можешь еще сам залить,
            //  придется удалять приложение и снова скачивать
        }
        /////////////

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}

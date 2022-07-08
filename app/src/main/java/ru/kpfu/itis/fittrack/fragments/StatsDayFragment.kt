package ru.kpfu.itis.fittrack.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.databinding.FragmentStatsDayBinding
import ru.kpfu.itis.fittrack.fragments.statsday.WaterAdapter

class StatsDayFragment : Fragment(R.layout.fragment_stats_day) {
    private var _binding: FragmentStatsDayBinding? = null
    private val binding get() = _binding!!

    private var goalCalories = 0
    private var consumedCalories = 0
    private var burnedCalories = 0
    private var consumedProteins = 0
    private var consumedFat = 0
    private var consumedCarbs = 0
    private var maxProteins = 0
    private var maxCarbs = 0
    private var maxFat = 0
    private var water = -0.25
    private var count = 1

    companion object{
        const val WATER_CUP_WEIGHT = 0.25
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStatsDayBinding.bind(view)
        init()
        initRecycler()
    }

    private fun onItemClick(int: Int) {
        water += WATER_CUP_WEIGHT
        binding.tvWater.text = "$water L"
        count++
        binding.rvCups.adapter = WaterAdapter(List(count) { it }, Glide.with(this)) {
            onItemClick(int)
        }
    }

    private fun initRecycler() {
        val adapter = WaterAdapter(List(count) { it }, Glide.with(this)) {
            water += WATER_CUP_WEIGHT
            onItemClick(it)
        }
        binding.rvCups.adapter = adapter
        binding.rvCups.layoutManager = GridLayoutManager(requireContext(), 10)
    }

    // мок, жду пока сделают экранчики, буду доставать из шп
    private fun init() {
        goalCalories = 100
        consumedCalories = 10
        burnedCalories = 6
        consumedProteins = 1
        consumedFat = 1
        consumedCarbs = 1
        maxCarbs = 100
        maxFat = 100
        maxProteins = 100
        with(binding) {
            tvEaten.text = "$consumedCalories \n eaten"
            tvBurned.text = "$burnedCalories \n burned"
            tvProgress.text = "${goalCalories - (consumedCalories - burnedCalories)} \n kcal left"
            pbGoal.progress = consumedCalories - burnedCalories
            pbGoal.max = goalCalories
            tvProteinsLeft.text = "$consumedProteins / $maxProteins"
            tvCarbsLeft.text = "$consumedCarbs / $maxCarbs"
            tvFatLeft.text = "$consumedFat / $maxFat"
            pbProteins.progress = 0
            pbProteins.max = maxProteins
            pbCarbs.progress = 0
            pbCarbs.max = maxCarbs
            pbFat.progress = 0
            pbFat.max = maxCarbs
        }
    }
}

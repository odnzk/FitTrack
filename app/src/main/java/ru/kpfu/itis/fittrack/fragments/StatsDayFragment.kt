package ru.kpfu.itis.fittrack.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.databinding.FragmentStatsDayBinding

class StatsDayFragment : Fragment(R.layout.fragment_stats_day) {
    private var _binding: FragmentStatsDayBinding? = null
    private val binding get() = _binding!!
    private var goalCalories: Int = 0
    private var consumedCalories: Int = 0
    private var burnedCalories: Int = 0
    private var consumedProteins: Int = 0
    private var consumedFat: Int = 0
    private var consumedCarbs: Int = 0
    private var maxProteins: Int = 0
    private var maxCarbs: Int = 0
    private var maxFat: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStatsDayBinding.bind(view)
        init()
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
            tvProgress.text = "${goalCalories-(consumedCalories-burnedCalories)} \n kcal left"
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

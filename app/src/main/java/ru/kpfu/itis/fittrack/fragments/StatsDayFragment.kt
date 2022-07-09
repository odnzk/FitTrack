package ru.kpfu.itis.fittrack.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.databinding.FragmentStatsDayBinding
import ru.kpfu.itis.fittrack.fragments.statsday.WaterAdapter
import ru.kpfu.itis.fittrack.viewpager.ReceivingInformationFragment

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
    private var sex = false
    private var height = 0
    private var weight = 0.0f
    private var age = 0
    private var activeness = ""
    private var goal = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStatsDayBinding.bind(view)
        init()
        initRecycler()
    }

    private fun init() {
        val sharedPref = activity?.getSharedPreferences(
            getString(R.string.preferenceFileKey_UserData),
            Context.MODE_PRIVATE
        )
        val sp = PreferenceManager.getDefaultSharedPreferences(requireContext())
        initUserData(sp)
        calcPFC()
        goalCalories = calcGoalCalories()
        burnedCalories = 6 // ждём
        calculateConsumed(sharedPref!!)
        drawProgressBars()
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

    private fun initUserData(sharedPref: SharedPreferences?) {
        sex = sharedPref?.getString(ReceivingInformationFragment.GENDER_KEY, "false")!!.toBoolean()
        height = sharedPref.getString(ReceivingInformationFragment.HEIGHT_KEY, "0")!!.toInt()
        weight = sharedPref.getString(ReceivingInformationFragment.WEIGHT_KEY, "0f")!!.toFloat()
        age = sharedPref.getString(ReceivingInformationFragment.AGE_KEY, "0")!!.toInt()
        activeness =
            sharedPref.getString(ReceivingInformationFragment.ACTIVENESS_KEY, activeness).toString()
        goal = sharedPref.getString(ReceivingInformationFragment.GOAL_KEY, goal).toString()
    }

    private fun calcPFC() {
        when (activeness) {
            "minimum", "average" -> {
                maxProteins = (weight * PROTEINS_MEDIUM).toInt()
                maxFat = (weight * FAT_MEDIUM).toInt()
                maxCarbs = (weight * CARBS_MEDIUM).toInt()
            }
            "maximum" -> {
                maxProteins = (weight * PROTEINS_HARD).toInt()
                maxFat = (weight * FAT_HARD).toInt()
                maxCarbs = (weight * CARBS_HARD).toInt()
            }
        }
    }


    private fun calcGoalCalories() = (calcActivenessCoef() * when (sex) {
        false -> MALE_CONST + weight * MALE_WEIGHT + height * MALE_HEIGHT - MALE_AGE * age
        true -> FEMALE_CONST + weight * FEMALE_WEIGHT + height * FEMALE_HEIGHT - FEMALE_AGE * age
    }).toInt() + calcGoalDiff()


    private fun calcActivenessCoef() =
        when (activeness) {
            "minimum" -> LOW_ACTIVENESS
            "average" -> AVERAGE_ACTIVENESS
            "maximum" -> MAX_ACTIVENESS
            else -> {
                0.0
            }
        }


    private fun calcGoalDiff() =
        when (goal) {
            "lose weight" -> -100
            "keep the same" -> 0
            "gain weight" -> 100
            else -> {
                0
            }
        }

    private fun calculateConsumed(sharedPref: SharedPreferences) {
        consumedCalories = sharedPref.getInt(ProductDescriptionFragment.EATEN_CALORIES, 0)
        consumedProteins =
            (sharedPref.getFloat(ProductDescriptionFragment.EATEN_PROTEINS, 0f)).toInt()
        consumedFat = (sharedPref.getFloat(ProductDescriptionFragment.EATEN_FATS, 0f)).toInt()
        consumedCarbs =
            (sharedPref.getFloat(ProductDescriptionFragment.EATEN_CARBS, 0f)).toInt()
    }

    private fun drawProgressBars() {
        with(binding) {
            tvEaten.text = "$consumedCalories \n eaten"
            tvBurned.text = "$burnedCalories \n burned"
            if (goalCalories - (consumedCalories - burnedCalories) >= 0) {
                tvProgress.text =
                    "${goalCalories - (consumedCalories - burnedCalories)} \n kcal left"
            } else {
                tvProgress.text =
                    "${(consumedCalories - burnedCalories) - goalCalories} \n kcal over limit"
            }
            pbGoal.max = goalCalories
            pbGoal.progress = consumedCalories - burnedCalories
            tvProteinsLeft.text = "$consumedProteins / $maxProteins"
            tvCarbsLeft.text = "$consumedCarbs / $maxCarbs"
            tvFatLeft.text = "$consumedFat / $maxFat"
            pbProteins.max = maxProteins
            pbProteins.progress = consumedProteins
            pbCarbs.max = maxCarbs
            pbCarbs.progress = consumedCarbs
            pbFat.max = maxFat
            pbFat.progress = consumedFat
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
        drawProgressBars()
    }

    companion object {
        const val WATER_CUP_WEIGHT = 0.25
        const val LOW_ACTIVENESS = 1.2
        const val AVERAGE_ACTIVENESS = 1.4
        const val MAX_ACTIVENESS = 1.9
        const val MALE_CONST = 66.5
        const val MALE_WEIGHT = 13.75
        const val MALE_HEIGHT = 5.003
        const val MALE_AGE = 6.775
        const val FEMALE_CONST = 655.1
        const val FEMALE_WEIGHT = 9.563
        const val FEMALE_HEIGHT = 1.85
        const val FEMALE_AGE = 4.676
        const val PROTEINS_MEDIUM = 1.5
        const val FAT_MEDIUM = 0.8
        const val CARBS_MEDIUM = 2
        const val PROTEINS_HARD = 2
        const val FAT_HARD = 1.5
        const val CARBS_HARD = 4
    }
}

package ru.kpfu.itis.fittrack.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.databinding.FragmentStatsDayBinding
import ru.kpfu.itis.fittrack.viewpager.ReceivingInformationFragment

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
    }

    //TODO обработка того что калорий съедено больше чем цель
    private fun init() {
        val sharedPref = activity?.getSharedPreferences(
            getString(R.string.preferenceFileKey_UserData),
            Context.MODE_PRIVATE
        )
        initUserData(sharedPref)
        calcPFC()
        goalCalories = calcGoalCalories()
        burnedCalories = 6 // ждём
        calculateConsumed(sharedPref!!)
        drawProgressBars()
    }
    private fun calculateConsumed(sharedPref:SharedPreferences){
        consumedCalories = sharedPref.getInt(ProductDescriptionFragment.EATEN_CALORIES, 0) ?: 0
        consumedProteins =
            (sharedPref.getFloat(ProductDescriptionFragment.EATEN_PROTEINS, 0f) ?: 0).toInt()
        consumedFat = (sharedPref.getFloat(ProductDescriptionFragment.EATEN_FATS, 0f) ?: 0).toInt()
        consumedCarbs =
            (sharedPref.getFloat(ProductDescriptionFragment.EATEN_CARBS, 0f) ?: 0).toInt()
    }
    //TODO учитывать активность и цель
    private fun calcGoalCalories(): Int {
        return when (sex) {
            false -> MALE_CONST + weight * MALE_WEIGHT + height * MALE_HEIGHT - MALE_AGE * age
            true -> FEMALE_CONST + weight * FEMALE_WEIGHT + height * FEMALE_HEIGHT - FEMALE_AGE * age
        }.toInt()
    }

    private fun initUserData(sharedPref: SharedPreferences?) {
        sex = sharedPref?.getBoolean(ReceivingInformationFragment.GENDER_KEY, sex)!!
        height = sharedPref.getInt(ReceivingInformationFragment.HEIGHT_KEY, height)
        weight = sharedPref.getFloat(ReceivingInformationFragment.WEIGHT_KEY, weight)
        age = sharedPref.getInt(ReceivingInformationFragment.AGE_KEY, age)
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

    private fun drawProgressBars() {
        with(binding) {
            tvEaten.text = "$consumedCalories \n eaten"
            tvBurned.text = "$burnedCalories \n burned"
            tvProgress.text = "${goalCalories - (consumedCalories - burnedCalories)} \n kcal left"
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
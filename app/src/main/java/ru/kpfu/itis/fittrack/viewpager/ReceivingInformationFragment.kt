package ru.kpfu.itis.fittrack.viewpager

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.databinding.FragmentReceivingInformationBinding


class ReceivingInformationFragment : Fragment() {
    private var _binding: FragmentReceivingInformationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReceivingInformationBinding.inflate(inflater, container, false)


        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.vp_spinner_activeness,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerActiveness.adapter = adapter
        }
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.vp_spinner_goal,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerGoal.adapter = adapter
        }


        var gender: Boolean? = null
        with(binding) {
            materialButtonToggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
                if (isChecked) {
                    gender = when (checkedId) {
                        R.id.btnFemale -> true
                        else -> {
                            false
                        }
                    }
                }
            }

            btnSaveUser.setOnClickListener {
                if (!isEmpty(etAge, etHeight, etWeight) && isCorrect(etWeight, etHeight, etAge)) {
                    gender?.let {
                        saveUserData(
                            it, etAge.text.toString().toInt(),
                            etHeight.text.toString().toInt(),
                            etWeight.text.toString().toFloat(),
                            binding.spinnerActiveness.selectedItem.toString(),
                            binding.spinnerGoal.selectedItem.toString()
                        )
                        activity?.findViewById<ViewPager2>(R.id.view_pager2)?.visibility = View.GONE
                        makeNavigationVisible()
                        hideKeyboard(etAge)
                    }
                }
            }
        }

        return binding.root
    }

    private fun hideKeyboard(view: View) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isEmpty(vararg list: EditText): Boolean {
        var flag = false
        for (item in list) {
            if (item.text.isEmpty()) {
                setError(item, "empty")
                flag = true
            }
        }
        return flag
    }

    private fun isCorrect(vararg list: EditText): Boolean {
        var flag = true
        for (item in list) {
            val number = item.text.toString().toInt()
            when (item.id) {
                R.id.etAge -> {
                    if (number !in 1..150) {
                        setError(item)
                        flag = false
                    }
                }
                else -> {
                    if (number !in 1..250) {
                        setError(item)
                        flag = false
                    }
                }
            }
        }
        return flag
    }

    private fun setError(editText: EditText, flag: String = "") {
        when (flag) {
            "empty" -> editText.error =
                resources.getString(R.string.vp_errorMessageEmptiness)
            else -> editText.error = resources.getString(R.string.vp_errorMessageInvalidity)
        }
    }

    private fun saveUserData(
        gender: Boolean,
        age: Int,
        height: Int,
        weight: Float,
        activeness: String,
        goal: String
    ) {
        val sp = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val editor = sp.edit()
        editor.putString(GENDER_KEY, gender.toString())
        editor.putString(HEIGHT_KEY, height.toString())
        editor.putString(WEIGHT_KEY, weight.toString())
        editor.putString(AGE_KEY, age.toString())
        editor.putString(ACTIVENESS_KEY, activeness)
        editor.putString(GOAL_KEY, goal)
        editor.apply()
    }

    private fun makeNavigationVisible() {
        activity?.findViewById<BottomAppBar>(R.id.bottomAppBar)?.visibility = View.VISIBLE
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.visibility =
            View.VISIBLE
        activity?.findViewById<FloatingActionButton>(R.id.fab)?.visibility = View.VISIBLE
    }

    companion object PreferencesKeysForSavingUserDataAndIsColdBoot {
        const val GENDER_KEY = "list_gender"
        const val HEIGHT_KEY = "et_height"
        const val WEIGHT_KEY = "et_weight"
        const val ACTIVENESS_KEY = "dropdown_activeness"
        const val GOAL_KEY = "dropdown_goal"
        const val AGE_KEY = "et_age"
        const val IS_FIRST_TIME_RUNNING = "isFirstTimeRunning"
    }
}

package ru.kpfu.itis.fittrack.viewpager

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
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

        var gender: Boolean? = null
        with(binding) {
            materialButtonToggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
                if (isChecked) {
                    when (checkedId) {
                        R.id.btnFemale -> gender = true
                        R.id.btnMale -> gender = false
                    }
                }
            }
            btnSaveUser.setOnClickListener {
                if (!isEmpty(etAge, etHeight, etWeight) && isCorrect(
                        etWeight,
                        etHeight,
                        etAge
                    ) && gender !== null
                ) {
                    saveUserData(
                        gender!!,
                        etAge.text.toString().toInt(),
                        etHeight.text.toString().toInt(),
                        etWeight.text.toString().toFloat()
                    )
                    activity?.findViewById<ViewPager2>(R.id.view_pager2)?.visibility = View.GONE
                }
            }
        }

        return binding.root
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
            "empty" -> editText.error = resources.getString(R.string.vp_errorMessageEmptiness)
            else -> editText.error = resources.getString(R.string.vp_errorMessageInvalidity)
        }
    }

    private fun saveUserData(gender: Boolean, age: Int, height: Int, weight: Float) {
        val sp = activity?.getSharedPreferences(
            getString(R.string.preferenceFileKey_UserData),
            Context.MODE_PRIVATE
        )
        val editor = sp?.edit()
        editor?.putBoolean(GENDER_KEY, gender)
        editor?.putInt(HEIGHT_KEY, height)
        editor?.putFloat(WEIGHT_KEY, weight)
        editor?.putInt(AGE_KEY, age)
        editor?.apply()
    }

    companion object PreferencesKeysForSavingUserDataAndIsColdBoot {
        const val GENDER_KEY = "vp-gender"
        const val HEIGHT_KEY = "vp-height"
        const val WEIGHT_KEY = "vp-weight"
        const val AGE_KEY = "vp-age"
        const val IS_FIRST_TIME_RUNNING = "isFirstTimeRunning"
    }
}

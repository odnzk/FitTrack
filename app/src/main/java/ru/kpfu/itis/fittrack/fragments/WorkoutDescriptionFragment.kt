package ru.kpfu.itis.fittrack.fragments

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.data.Training
import ru.kpfu.itis.fittrack.databinding.FragmentWorkoutDescriptionBinding

class WorkoutDescriptionFragment : Fragment(R.layout.fragment_workout_description) {
    private var _binding: FragmentWorkoutDescriptionBinding? = null
    private val binding get() = _binding!!
    private lateinit var training: Training
    private lateinit var curWorkout: Training
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWorkoutDescriptionBinding.bind(view)
        curWorkout = (arguments?.getSerializable(ARG_TEXT) as Training)
        binding.tvTitle.text = curWorkout?.title
        Glide.with(this).load(curWorkout?.picture).into(binding.ivPicture)
        binding.tvCalories.text = curWorkout?.calories.toString() + " calories\n per workout"
        var newCalories: Int? = curWorkout?.calories


        binding.seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                newCalories = Math.round(((curWorkout?.calories!!.toFloat())*(progress.toFloat()/60)).toDouble()).toInt()
                binding.tvCalories.text = "${newCalories} calories\n per workout"
                binding.tvTime.text = "Training time:\n$progress minutes"
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
        })

        binding.btnAddItem.setOnClickListener {
            training = Training(curWorkout.id, curWorkout.title, newCalories!!, curWorkout.picture, curWorkout.category)
            changeSharedPref(training)
            addingValuesToSharedPreferencesExtension(binding.root.context, training.category, "Training", training)
        }

    }

    companion object {
        private const val ARG_TEXT = "Training"
        fun create(item: Training): Bundle {
            val bundle = Bundle()
            bundle.putSerializable(
                ARG_TEXT,
                item
            )
            return bundle
        }

    }

}
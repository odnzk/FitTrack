package ru.kpfu.itis.fittrack.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.WorkoutAdapter
import ru.kpfu.itis.fittrack.WorkoutRepository
import ru.kpfu.itis.fittrack.databinding.FragmentWorkoutBinding

class WorkoutFragment : Fragment(R.layout.fragment_workout) {
    private var _binding: FragmentWorkoutBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWorkoutBinding.bind(view)
        val adapter = WorkoutAdapter(Glide.with(this)) {
            //TODO: логика добавления по нажатию на элемент рекуклера, будет диалоговое окно
            //добавить в список на день? Да нет
        }
        binding.rvList.adapter = adapter
        binding.rvList.layoutManager =
            GridLayoutManager(requireContext(), 3)


    }
}

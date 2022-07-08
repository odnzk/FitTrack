package ru.kpfu.itis.fittrack

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ru.kpfu.itis.fittrack.data.Training
import ru.kpfu.itis.fittrack.databinding.ItemWorkoutBinding

class WorkoutHolder (var binding: ItemWorkoutBinding,
private var glide: RequestManager,
private var onItemClick: (Training) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(a: Training) {
        with(binding) {
            binding.tvWorkout.text = a.title
            binding.tvCategory.text = a.category
            binding.tvCalories.text = "Calories: ${a.calories}"
            root.setOnClickListener {
                onItemClick(a)
            }
            glide
                .load(a.picture)
                .placeholder(ru.kpfu.itis.fittrack.R.drawable.defworkout)
                .error(ru.kpfu.itis.fittrack.R.drawable.defworkout)
                .into(binding.ivWorkout)
        }
    }
}
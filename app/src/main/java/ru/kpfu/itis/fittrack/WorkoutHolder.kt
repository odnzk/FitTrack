package ru.kpfu.itis.fittrack

import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ru.kpfu.itis.fittrack.data.Workout
import ru.kpfu.itis.fittrack.databinding.ItemWorkoutBinding

class WorkoutHolder (var binding: ItemWorkoutBinding,
private var glide: RequestManager,
private var onItemClick: (Workout) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(a: Workout) {
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
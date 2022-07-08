package ru.kpfu.itis.fittrack

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ru.kpfu.itis.fittrack.data.Training
import ru.kpfu.itis.fittrack.databinding.ItemWorkoutBinding

class WorkoutAdapter(
    private val glide: RequestManager,
    private val onItemClick: (Training) -> Unit
): RecyclerView.Adapter<WorkoutHolder>() {

    private var workoutList = WorkoutRepository.workoutList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutHolder{
        return WorkoutHolder(
            binding = ItemWorkoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            glide = glide,
            onItemClick = onItemClick
        )
    }

    override fun getItemCount(): Int {
        return workoutList.size
    }

    override fun onBindViewHolder(holder: WorkoutHolder, position: Int) {
        val currentItem = workoutList[position]
        holder.onBind(currentItem)
    }

}
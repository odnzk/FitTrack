package ru.kpfu.itis.fittrack.listForTheDay

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.fittrack.databinding.TrainingFoodForTheDayItemBinding
import java.lang.ref.WeakReference

class ListForTheDayViewHolder (
    private val binding: TrainingFoodForTheDayItemBinding,
    private val onItemClick: (Pair<ItemForTheDay, TrainingFoodForTheDayItemBinding>) -> Unit,
        ) : RecyclerView.ViewHolder (binding.root){

    fun onBind(itemForTheDay: ItemForTheDay) {
        itemView.scrollTo(0, 0)
        with(binding) {
            tvName.text = itemForTheDay.name
            val kCal = itemForTheDay.kCal
            tvCKal.text = "$kCal  cKal"
            root.setOnClickListener {
                onItemClick.invoke(itemForTheDay to binding)
            }
        }

    }
}
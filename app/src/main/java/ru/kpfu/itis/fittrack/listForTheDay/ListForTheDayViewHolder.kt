package ru.kpfu.itis.fittrack.listForTheDay

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.fittrack.databinding.ListForTheDayItemBinding
import java.lang.ref.WeakReference

class ListForTheDayViewHolder (
    private val binding: ListForTheDayItemBinding,
    private val onItemClick: (Pair<ItemForTheDay, ListForTheDayItemBinding>) -> Unit,
        ) : RecyclerView.ViewHolder (binding.root){

    fun onBind(itemForTheDay: ItemForTheDay) {
        itemView.scrollTo(0, 0)
        with(binding) {
            tvName.text = itemForTheDay.name
            tvCKal.text = itemForTheDay.kCal
            root.setOnClickListener {
                onItemClick.invoke(itemForTheDay to binding)
            }
        }

    }
}
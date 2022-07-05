package ru.kpfu.itis.fittrack.listForTheDay

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.fittrack.databinding.ListForTheDayItemBinding

class ListForTheDayAdapter(
    private val list: MutableList<ItemForTheDay>,
    private val onItemClick: (Pair<ItemForTheDay, ListForTheDayItemBinding>) -> Unit
) : RecyclerView.Adapter<ListForTheDayViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListForTheDayViewHolder =
        ListForTheDayViewHolder(
            ListForTheDayItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClick = onItemClick
        )


    override fun onBindViewHolder(holder: ListForTheDayViewHolder, position: Int) {
        holder.onBind(list[position])

    }
    @SuppressLint("NotifyDataSetChanged")
    fun deleteItem(i : Int) {
        list.removeAt(i)
        notifyDataSetChanged()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun addItem(i:Int, itemForTheDay: ItemForTheDay) {
        list.add(i, itemForTheDay)
        notifyDataSetChanged()
    }

    fun getItemList() : List<ItemForTheDay>{
        return list
    }

    override fun getItemCount(): Int = list.size

}
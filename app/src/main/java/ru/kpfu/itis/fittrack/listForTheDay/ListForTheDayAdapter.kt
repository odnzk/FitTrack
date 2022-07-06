package ru.kpfu.itis.fittrack.listForTheDay

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.fittrack.data.BaseEntity
import ru.kpfu.itis.fittrack.databinding.TrainingFoodForTheDayItemBinding

class ListForTheDayAdapter(
    private val list: MutableList<BaseEntity>,
    private val onItemClick: (Pair<BaseEntity, TrainingFoodForTheDayItemBinding>) -> Unit
) : RecyclerView.Adapter<ListForTheDayViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListForTheDayViewHolder =
        ListForTheDayViewHolder(
            TrainingFoodForTheDayItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClick = onItemClick
        )


    override fun onBindViewHolder(holder: ListForTheDayViewHolder, position: Int) {
        holder.onBind(list[position])

    }


    // it could not be worse, but it works.................
    // don't judge
    @SuppressLint("NotifyDataSetChanged")
    fun deleteItem(id: Int, context: Context) {
        val item = list.get(id)
        val sharedPreferencesStorage = SharedPreferencesStorage(context)
        val idSArr = sharedPreferencesStorage.loadFood()?.split(" ")?.toMutableList()
        val categories = sharedPreferencesStorage.loadCategories()?.split(" ")?.toMutableList()

        var i = 0
        if (idSArr != null) {
            for (idd in idSArr) {
                val category = categories?.get(i)
                if (idd == (item.id - 2).toString() && category == item.category) {
                    break
                }
            }
            i++
        }
        idSArr?.removeAt(i)
        categories?.removeAt(i)
        sharedPreferencesStorage.clearAll()
        if (idSArr != null) {
            for ((s, k) in idSArr.withIndex()) {
                val category = categories?.get(s)
                if (!k.isNullOrBlank() && !category.isNullOrBlank()) {
                    sharedPreferencesStorage.addCategory(category)
                    sharedPreferencesStorage.addFoodItem(k.toInt())
                }
            }
        }
        list.removeAt(id)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(i: Int, itemForTheDay: BaseEntity) {
        list.add(i, itemForTheDay)
        val selector: (BaseEntity) -> Int = { item -> item.category.length }
        list.sortBy(selector)
        list.reverse()
        notifyDataSetChanged()
    }

    fun getItemList(): List<BaseEntity> {
        return list
    }

    override fun getItemCount(): Int = list.size

}
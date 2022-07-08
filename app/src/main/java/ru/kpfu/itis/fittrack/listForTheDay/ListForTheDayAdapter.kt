package ru.kpfu.itis.fittrack.listForTheDay

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.fittrack.data.BaseEntity
import ru.kpfu.itis.fittrack.databinding.TrainingFoodForTheDayItemBinding
import android.app.Activity
import android.widget.Toast
import ru.kpfu.itis.fittrack.data.Product
import ru.kpfu.itis.fittrack.data.Recipe
import ru.kpfu.itis.fittrack.fragments.ProductDescriptionFragment

class ListForTheDayAdapter(
    private val list: MutableList<BaseEntity>,
    private val onItemClick: (BaseEntity) -> Unit
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
    fun deleteItem(id: Int, context: Context, activity: Activity) {
        val item = list.get(id)
        changeSharedPref(activity, item)
        val sharedPreferencesStorage = SharedPreferencesStorage(context)
        val idSArr = sharedPreferencesStorage.loadIDS()?.split(" ")?.toMutableList()
        val categories = sharedPreferencesStorage.loadCategories()?.split(" ")?.toMutableList()
        val types = sharedPreferencesStorage.loadTypes()?.split(" ")?.toMutableList()
        val calories = sharedPreferencesStorage.loadCalories()?.split(" ")?.toMutableList()
        var i = 0

        if (idSArr != null) {
            for (idd in idSArr) {
                val category = categories?.get(i)
                val type = types?.get(i)
                if (type == "Training") {
                    val calorie = calories?.get(i)?.toInt()
                    if (idd == (item.id).toString() && category == item.category && type == item.type && item.calories == calorie) {
                        break
                    }
                } else {
                    if (idd == (item.id - 1).toString() && category == item.category && type == item.type) {
                        break
                    }
                }
                i++
            }
        }

        if (i == idSArr?.size) {
            Toast.makeText(context, "Couldn't delete your item completely", Toast.LENGTH_SHORT)
                .show()
        } else {
            idSArr?.removeAt(i)
            categories?.removeAt(i)
            types?.removeAt(i)
            calories?.removeAt(i)

            sharedPreferencesStorage.clearAll()
            if (idSArr != null) {
                for ((s, k) in idSArr.withIndex()) {
                    val category = categories?.get(s)
                    val type = types?.get(s)
                    val kCal = calories?.get(s) ?: ""
                    if (k.isNotBlank() && !category.isNullOrBlank() && !type.isNullOrBlank()) {
                        sharedPreferencesStorage.addCategory(category)
                        sharedPreferencesStorage.addItemID(k.toInt())
                        sharedPreferencesStorage.addType(type)
                        sharedPreferencesStorage.addCalorieCount(kCal)
                    }
                }
            }
            list.removeAt(id)
            notifyDataSetChanged()
        }
    }



    @SuppressLint("NotifyDataSetChanged")
    fun addItem(i: Int, itemForTheDay: BaseEntity, context: Context) {
        list.add(i, itemForTheDay)
        list.sortWith(categoriesComparator())
        notifyDataSetChanged()
    }


    fun getItemList(): List<BaseEntity> {
        return list
    }

    override fun getItemCount(): Int = list.size

    private fun categoriesComparator() = Comparator<BaseEntity>{ a, b ->
        when {
            (b.category == "Dinner" && a.category == "Lunch") -> -1
            (a.category == "Dinner" && b.category == "Lunch") -> 1
            else -> b.category.length - a.category.length
        }
    }
    private fun changeSharedPref(activity: Activity, baseEntity: BaseEntity) {
        val sharedPref = activity.getSharedPreferences(
            "UserData",
            Context.MODE_PRIVATE
        )
        var eatenCalories = sharedPref?.getInt(ProductDescriptionFragment.EATEN_CALORIES, 0)
        var eatenProteins = sharedPref?.getFloat(ProductDescriptionFragment.EATEN_PROTEINS, 0f)
        var eatenCarbs = sharedPref?.getFloat(ProductDescriptionFragment.EATEN_CARBS, 0f)
        var eatenFats = sharedPref?.getFloat(ProductDescriptionFragment.EATEN_FATS, 0f)
        val editor = sharedPref?.edit()

        eatenCalories = eatenCalories?.minus(baseEntity.calories)
        if (baseEntity is Product) {
            eatenProteins = eatenProteins?.minus(baseEntity.proteins)
            eatenCarbs = eatenCarbs?.minus(baseEntity.carbohydrates)
            eatenFats = eatenFats?.minus(baseEntity.fats)
        }
        if (baseEntity is Recipe) {
            eatenProteins = eatenProteins?.minus(baseEntity.proteins)
            eatenCarbs = eatenCarbs?.minus(baseEntity.carbohydrates)
            eatenFats = eatenFats?.minus(baseEntity.fats)
        }

        editor?.putInt(ProductDescriptionFragment.EATEN_CALORIES, eatenCalories!!)?.apply()
        editor?.putFloat(ProductDescriptionFragment.EATEN_PROTEINS, eatenProteins!!)?.apply()
        editor?.putFloat(ProductDescriptionFragment.EATEN_CARBS, eatenCarbs!!)?.apply()
        editor?.putFloat(ProductDescriptionFragment.EATEN_FATS, eatenFats!!)?.apply()

    }
}


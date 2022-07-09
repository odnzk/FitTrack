package ru.kpfu.itis.fittrack.fragments

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.kpfu.itis.fittrack.data.BaseEntity
import ru.kpfu.itis.fittrack.listForTheDay.SharedPreferencesStorage

fun Fragment.addingValuesToSharedPreferencesExtension(
    context: Context,
    category: String,
    typeOfItem: String,
    item: BaseEntity?
) {
    val sharedPreferencesStorage = SharedPreferencesStorage(context)

    item?.let { it1 -> Integer.valueOf(it1.id) }
        ?.let { it2 -> sharedPreferencesStorage.addItemID(it2) }

    sharedPreferencesStorage.addCategory(category)
    sharedPreferencesStorage.addType(typeOfItem)
    sharedPreferencesStorage.addCalorieCount(item?.calories.toString())
    Toast.makeText(
        context,
        item?.title + " has been added to the day list",
        Toast.LENGTH_SHORT
    ).show()
}
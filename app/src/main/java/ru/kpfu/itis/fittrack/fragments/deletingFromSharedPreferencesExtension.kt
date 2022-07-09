package ru.kpfu.itis.fittrack.fragments

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.kpfu.itis.fittrack.listForTheDay.SharedPreferencesStorage

fun Fragment.deleteFromSharedPreferences(idToDelete: Int, typeToDelete:String, context: Context) {
    val sharedPreferencesStorage = SharedPreferencesStorage(context)
    val idsArr = sharedPreferencesStorage.loadIDS()?.split(" ")?.toMutableList()
    val typesArr = sharedPreferencesStorage.loadTypes()?.split(" ")?.toMutableList()
    val categoryArr = sharedPreferencesStorage.loadCategories()?.split(" ")?.toMutableList()
    val caloriesArr = sharedPreferencesStorage.loadCalories()?.split(" ")?.toMutableList()

    for ((i, index) in idsArr!!.withIndex()) {
        if(index.isNotBlank()) {
            val type = typesArr?.get(i)
            if (index.toInt() == idToDelete && type == typeToDelete) {
                idsArr[i] = ""
            }
        }
    }
    sharedPreferencesStorage.clearAll()

    for ((i, index) in idsArr.withIndex()) {
        if(index.isNotBlank()) {
            val type = typesArr?.get(i)
            val category = categoryArr?.get(i)
            val calorie = caloriesArr?.get(i)
            sharedPreferencesStorage.addItemID(index.toInt())
            if (calorie != null) {
                sharedPreferencesStorage.addCalorieCount(calorie)
            }
            if (category != null) {
                sharedPreferencesStorage.addCategory(category)
            }
            if (type != null) {
                sharedPreferencesStorage.addType(type)
            }
        }
    }
}
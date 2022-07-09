package ru.kpfu.itis.fittrack.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import ru.kpfu.itis.fittrack.listForTheDay.SharedPreferencesStorage

fun Fragment.deleteFromSharedPreferences(idToDelete: Int, typeToDelete:String, context: Context) {
    val sharedPreferencesStorage = SharedPreferencesStorage(context)
    val idsArr = sharedPreferencesStorage.loadIDS()?.split(" ")?.toMutableList()
    val typesArr = sharedPreferencesStorage.loadTypes()?.split(" ")?.toMutableList()
    val categoryArr = sharedPreferencesStorage.loadCategories()?.split(" ")?.toMutableList()
    val caloriesArr = sharedPreferencesStorage.loadCalories()?.split(" ")?.toMutableList()

    for (i in 0..idsArr!!.size-2) {
        val type = typesArr!![i]
        val id = idsArr[i]
        if (id.isNotBlank()) {
            if (idsArr[i].toInt() == idToDelete && type == typeToDelete) {
                idsArr.removeAt(i)
                typesArr.removeAt(i)
                categoryArr?.removeAt(i)
                caloriesArr?.removeAt(i)
            }
        }
    }
    sharedPreferencesStorage.clearAll()
    for (i in 0 until idsArr.size) {
        if(idsArr[i].isNotBlank()) {
            val type = typesArr!![i]
            val category = categoryArr!![i]
            val idToAdd  = idsArr[i]
            val calorie = caloriesArr?.get(i)
            sharedPreferencesStorage.addType(type)
            sharedPreferencesStorage.addItemID(idToAdd.toInt())
            sharedPreferencesStorage.addCategory(category)
            if (calorie != null) {
                sharedPreferencesStorage.addCalorieCount(calorie)
            }
        }
    }
}
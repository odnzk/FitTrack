package ru.kpfu.itis.fittrack.listForTheDay

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesStorage(private val context: Context) {
    private var idsFoodList: String? = null
    private var categories: String? = null
    private var types: String? = null
    private var calories: String? = null

    private fun saveData(allKey: String, genKey:String, stringToSave:String?) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(allKey, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.apply() {
            putString(genKey, stringToSave)

        }.apply()
    }

    private fun loadData(keyPrimary: String,keySecondary:String) : String? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(keyPrimary, Context.MODE_PRIVATE)
        return sharedPreferences.getString(keySecondary, null)
    }
    fun loadIDS(): String? {
        return loadData(Companion.KEY1, Companion.ID)
    }
    fun loadCategories(): String? {
        return loadData(Companion.KEY2, Companion.ID_CATEGORY)
    }
    fun loadTypes(): String? {
        return loadData(Companion.KEY3, Companion.ID_TYPE)
    }
    fun loadCalories(): String? {
        return loadData(Companion.KEY4, Companion.ID_CALORIE)
    }

    fun addItemID(id: Int) {
        if (loadIDS().isNullOrBlank()) {
            idsFoodList = "$id "
            saveData(Companion.KEY1, Companion.ID, idsFoodList)
        } else {
            idsFoodList = (loadIDS() + id.toString() + " ")
            saveData(Companion.KEY1, Companion.ID, idsFoodList)
        }
    }
    fun addCategory(category: String) {
        if (loadCategories().isNullOrBlank()) {
            categories = "$category "
            saveData(Companion.KEY2, Companion.ID_CATEGORY, categories)
        } else {
            categories = (loadCategories() + category + " ")
            saveData(Companion.KEY2, Companion.ID_CATEGORY, categories)
        }
    }
    fun addType(type: String) {
        if (loadTypes().isNullOrBlank()) {
            types = "$type "
            saveData(Companion.KEY3, Companion.ID_TYPE, types)
        } else {
            types = (loadTypes() + type + " ")
            saveData(Companion.KEY3, Companion.ID_TYPE, types)
        }
    }
    fun addCalorieCount(calorieCount: String) {
        if (loadCalories().isNullOrBlank()) {
            calories = "$calorieCount "
            saveData(Companion.KEY4, Companion.ID_CALORIE, calories)
        } else {
            calories = (loadCalories() + calorieCount + " ")
            saveData(Companion.KEY4, Companion.ID_CALORIE, calories)
        }
    }

    fun clearAll() {
        saveData(Companion.KEY1, Companion.ID, idsFoodList)
        saveData(Companion.KEY2, Companion.ID_CATEGORY, categories)
        saveData(Companion.KEY3, Companion.ID_TYPE, types)
        saveData(Companion.KEY4, Companion.ID_CALORIE, calories)
    }

    companion object {
        const val KEY1 = "FOOD_IDS"
        const val KEY2 = "CATEGORIES"
        const val KEY3 = "TYPES"
        const  val KEY4 = "CALORIES"
        const  val ID = "ID"
        const val ID_CATEGORY = "CATEGORY"
        const val ID_TYPE = "TYPE"
        const  val ID_CALORIE = "KCAL"
    }

}
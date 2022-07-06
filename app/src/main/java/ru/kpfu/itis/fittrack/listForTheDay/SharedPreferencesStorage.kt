package ru.kpfu.itis.fittrack.listForTheDay

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesStorage(private val context: Context) {
    private val KEY1 = "FOOD_IDS"
    private val KEY2 = "CATEGORIES"
    private val ID_KEY = "ID"
    private val ID_CATEGORY = "CATEGORY"
    private var idsFoodList: String? = null
    private var categories: String? = null

    private fun saveData(allKey: String, genKey:String, stringToSave:String?) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(allKey, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.apply() {
            putString(genKey, stringToSave)

        }.apply()
    }

    fun loadFood(): String? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(KEY1, Context.MODE_PRIVATE)
        return sharedPreferences.getString(ID_KEY, null)
    }

    fun loadCategories(): String? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(KEY2, Context.MODE_PRIVATE)
        return sharedPreferences.getString(ID_CATEGORY, null)
    }

    fun addFoodItem(id: Int) {
        if (loadFood().isNullOrBlank()) {
            idsFoodList = "$id "
            saveData(KEY1, ID_KEY, idsFoodList)
        } else {
            idsFoodList = (loadFood() + id.toString() + " ")
            saveData(KEY1, ID_KEY, idsFoodList)
        }
    }
    fun addCategory(category: String) {
        if (loadCategories().isNullOrBlank()) {
            categories = "$category "
            saveData(KEY2,ID_CATEGORY, categories)
        } else {
            categories = (loadCategories() + category + " ")
            saveData(KEY2,ID_CATEGORY, categories)
        }
    }
    fun clearAll() {
        saveData(KEY1, ID_KEY, idsFoodList)
        saveData(KEY2,ID_CATEGORY, categories)
    }

}
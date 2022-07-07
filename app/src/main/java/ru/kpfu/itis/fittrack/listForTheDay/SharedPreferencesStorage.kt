package ru.kpfu.itis.fittrack.listForTheDay

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesStorage(private val context: Context) {
    private val KEY1 = "FOOD_IDS"
    private val KEY2 = "CATEGORIES"
    private val KEY3 = "TYPES"
    private val ID = "ID"
    private val ID_CATEGORY = "CATEGORY"
    private val ID_TYPE = "TYPE"
    private var idsFoodList: String? = null
    private var categories: String? = null
    private var types: String? = null

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
        return loadData(KEY1, ID)
    }
    fun loadCategories(): String? {
        return loadData(KEY2, ID_CATEGORY)
    }
    fun loadTypes(): String? {
        return loadData(KEY3, ID_TYPE)
    }

    fun addItemID(id: Int) {
        if (loadIDS().isNullOrBlank()) {
            idsFoodList = "$id "
            saveData(KEY1, ID, idsFoodList)
        } else {
            idsFoodList = (loadIDS() + id.toString() + " ")
            saveData(KEY1, ID, idsFoodList)
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

    fun addType(type: String) {
        if (loadTypes().isNullOrBlank()) {
            types = "$type "
            saveData(KEY3,ID_TYPE, types)
        } else {
            types = (loadTypes() + type + " ")
            saveData(KEY3,ID_TYPE, types)
        }
    }

    fun clearAll() {
        saveData(KEY1, ID, idsFoodList)
        saveData(KEY2,ID_CATEGORY, categories)
        saveData(KEY3,ID_TYPE, types)
    }

}
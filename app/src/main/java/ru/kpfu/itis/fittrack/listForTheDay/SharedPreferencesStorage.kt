package ru.kpfu.itis.fittrack.listForTheDay

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesStorage(private val context: Context) {
    private var idsFoodList: String? = null
    private var categories: String? = null
    private var types: String? = null
    private var calories: String? = null
    private var proteins: String? = null
    private var fats: String? = null
    private var carbs: String? = null

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

    fun loadProteins(): String? {
        return loadData(Companion.KEY5, Companion.ID_P)
    }

    fun loadFats(): String? {
        return loadData(Companion.KEY6, Companion.ID_F)
    }

    fun loadCarbs(): String? {
        return loadData(KEY7, ID_C)
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

    fun addProteinCount(proteinCount: String) {
        if (loadProteins().isNullOrBlank()) {
            proteins = "$proteinCount "
            saveData(Companion.KEY5, Companion.ID_P, proteins)
        } else {
            proteins = (loadProteins() + proteinCount + " ")
            saveData(Companion.KEY5, Companion.ID_P, proteins)
        }
    }

    fun addFatCount(fatsCount: String) {
        if (loadFats().isNullOrBlank()) {
            fats = "$fatsCount "
            saveData(Companion.KEY6, Companion.ID_F, fats)
        } else {
            fats = (loadFats() + fatsCount + " ")
            saveData(Companion.KEY6, Companion.ID_F, fats)
        }
    }

    fun addCarbsCount(carbsCount: String) {
        if (loadCarbs().isNullOrBlank()) {
            carbs = "$carbsCount "
            saveData(Companion.KEY7, Companion.ID_C, carbs)
        } else {
            carbs = (loadCarbs() + carbsCount + " ")
            saveData(Companion.KEY7, Companion.ID_C, carbs)
        }
    }

    fun clearAll() {
        saveData(Companion.KEY1, Companion.ID, idsFoodList)
        saveData(Companion.KEY2, Companion.ID_CATEGORY, categories)
        saveData(Companion.KEY3, Companion.ID_TYPE, types)
        saveData(Companion.KEY4, Companion.ID_CALORIE, calories)
        saveData(KEY5, ID_P, proteins)
        saveData(KEY6, ID_F, fats)
        saveData(KEY7, ID_C, carbs)
    }

    companion object {
        const val KEY1 = "FOOD_IDS"
        const val KEY2 = "CATEGORIES"
        const val KEY3 = "TYPES"
        const val KEY4 = "CALORIES"
        const val KEY5 = "PROTEINS"
        const val KEY6 = "FATS"
        const val KEY7 = "CARBS"
        const val ID = "ID"
        const val ID_CATEGORY = "CATEGORY"
        const val ID_TYPE = "TYPE"
        const val ID_CALORIE = "KCAL"
        const val ID_P = "PROTEIN"
        const val ID_F = "FAT"
        const val ID_C = "CARBS"
    }

}
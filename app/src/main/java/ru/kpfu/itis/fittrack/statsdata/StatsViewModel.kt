package ru.kpfu.itis.fittrack.statsdata

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StatsViewModel(application: Application) : AndroidViewModel(application) {
    val getAllStats: LiveData<List<StatsItem>> =
        EntireDatabase.getDatabase(application).statsItemDao().getAllStatsItems()
//    private val repository: StatsRepository

//    init {
////        val recipeDao = RecipeDatabase.getDatabase(application).recipeDao()
////        val recipeDao = EntireDatabase.getDatabase(application).recipeDao()
////        repository = RecipeRepository(recipeDao)
//        //        getAllRecipes = repository.getAllRecipes
//    }

    fun addStatsItem(statsItem: StatsItem) {
        viewModelScope.launch(Dispatchers.IO) {
            EntireDatabase.getDatabase(getApplication()).statsItemDao().insert(statsItem)
        }
    }
}



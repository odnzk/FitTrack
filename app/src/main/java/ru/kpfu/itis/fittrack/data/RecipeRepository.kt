package ru.kpfu.itis.fittrack.data

import androidx.lifecycle.LiveData

class RecipeRepository(private val recipeDao: RecipeDao) {
    val getAllRecipes: LiveData<List<Recipe>> = recipeDao.getAllRecipes()
    suspend fun addRecipe(recipe: Recipe) {
        recipeDao.add(recipe)
    }

    suspend fun deleteRecipe(recipe: Recipe) {
        recipeDao.deleteRecipe(recipe)
    }


}
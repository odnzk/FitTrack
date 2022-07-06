package ru.kpfu.itis.fittrack.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecipeDao {
    @Query("SELECT * from Recipe ORDER BY id_recipe ASC")
    fun getAllRecipes(): LiveData<List<Recipe>>

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(recipe: Recipe)
}
package ru.kpfu.itis.fittrack.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface RecipeDao {
    @Query("SELECT * from Recipe ORDER BY id_recipe ASC")
    fun getAllRecipes(): LiveData<List<Recipe>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(recipe: Recipe)
}
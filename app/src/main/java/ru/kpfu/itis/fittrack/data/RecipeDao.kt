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

    @Query("SELECT * from Recipe WHERE id_recipe=:id")
    fun loadSingle(id: String): LiveData<Recipe>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(recipe: Recipe)
}
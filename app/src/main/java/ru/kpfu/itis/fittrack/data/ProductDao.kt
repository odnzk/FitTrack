package ru.kpfu.itis.fittrack.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {
    @Query("SELECT * from Product ORDER BY id_product ASC")
    fun getAllProducts(): LiveData<List<Product>>
//TODO: функция удаления элемента из бд
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(product: Product)

}
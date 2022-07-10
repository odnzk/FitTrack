package ru.kpfu.itis.fittrack.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {
    @Query("SELECT * from Product ORDER BY id_product ASC")
    fun getAllProducts(): LiveData<List<Product>>

    @Delete
    suspend fun deleteProduct(product: Product)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(product: Product)

}

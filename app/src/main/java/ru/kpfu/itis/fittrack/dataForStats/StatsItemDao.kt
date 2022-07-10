package ru.kpfu.itis.fittrack.dataForStats

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StatsItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewStatsItem(statsItem: StatsItem)

    @Delete
    fun delete(statsItem: StatsItem)


    @Query("SELECT * FROM stats_items")
    fun getAllStatsItems(): LiveData<List<StatsItem>>
}

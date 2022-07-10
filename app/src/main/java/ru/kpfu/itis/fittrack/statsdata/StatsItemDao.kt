package ru.kpfu.itis.fittrack.statsdata

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StatsItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(statsItem: StatsItem)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(statsItem: StatsItem)

    @Query("SELECT * FROM stats_items")
    fun getAllStatsItems(): LiveData<List<StatsItem>>
}

package ru.kpfu.itis.fittrack.dataForStats

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["day", "month", "year"], tableName = "stats_items")
data class StatsItem(
//    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val day: Int,
    @ColumnInfo val month: Int,
    @ColumnInfo val year: Int,
    @ColumnInfo(name = "consumed_calories") val consumedCalories: Int,
    @ColumnInfo val proteins: Int,
    @ColumnInfo val fats: Int,
    @ColumnInfo val carbs: Int,
)

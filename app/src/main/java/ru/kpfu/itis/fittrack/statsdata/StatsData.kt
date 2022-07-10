package ru.kpfu.itis.fittrack.statsdata

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["day", "month", "year"], tableName = "stats_items")
data class StatsItem(
    @ColumnInfo val day: Int,
    @ColumnInfo val month: Int,
    @ColumnInfo val year: Int,
    @ColumnInfo(name = "consumed_calories") val consumedCalories: Int,
    @ColumnInfo val proteins: Float,
    @ColumnInfo val fats: Float,
    @ColumnInfo val carbs: Float,
)

package ru.kpfu.itis.fittrack.dataForStats

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [StatsItem::class], version = 1, exportSchema = false)
abstract class StatsItemDatabase : RoomDatabase() {
    abstract fun statsItemDao(): StatsItemDao

    companion object{
        @Volatile
        private var INSTANCE: StatsItemDatabase? = null

        fun getDatabase(context: Context): StatsItemDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StatsItemDatabase::class.java,
                    "db_stats_items"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

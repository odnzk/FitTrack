package ru.kpfu.itis.fittrack.dataForStats

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.kpfu.itis.fittrack.data.Product
import ru.kpfu.itis.fittrack.data.ProductDao
import ru.kpfu.itis.fittrack.data.Recipe
import ru.kpfu.itis.fittrack.data.RecipeDao

@Database(entities = [StatsItem::class, Product::class, Recipe::class], version = 1, exportSchema = false)
abstract class EntireDatabase : RoomDatabase() {
    abstract fun statsItemDao(): StatsItemDao
    abstract fun productDao(): ProductDao
    abstract fun recipeDao(): RecipeDao

    companion object{
        @Volatile
        private var INSTANCE: EntireDatabase? = null

        fun getDatabase(context: Context): EntireDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EntireDatabase::class.java,
                    "entire_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}

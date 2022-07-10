package ru.kpfu.itis.fittrack.statsdata

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.kpfu.itis.fittrack.InitialProducts
import ru.kpfu.itis.fittrack.InitialRecipes
import ru.kpfu.itis.fittrack.data.*

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
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val runnable: () -> Unit = {
                                for (elem in InitialProducts.list){
                                    INSTANCE?.productDao()?.add(elem)
                                }
                                for (elem in InitialRecipes.list){
                                    INSTANCE?.recipeDao()?.add(elem)
                                }
                                for (elem in InitialStatsItem.list){
                                    INSTANCE?.statsItemDao()?.add(elem)
                                }
                            }
                            Thread(runnable).start()
                        }
                    }).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}

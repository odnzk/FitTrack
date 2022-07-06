package ru.kpfu.itis.fittrack.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_recipe")
    override var id: Int,
    @ColumnInfo(name = "title_recipe")
    override var title: String,
    @ColumnInfo(name = "picture_recipe")
    var picture: String,
    @ColumnInfo
    var description: String,
    @ColumnInfo(name = "calories_recipe")
    override var calories: Int,
    @ColumnInfo(name = "proteins_recipe")
    var proteins: Float,
    @ColumnInfo(name = "fats_recipe")
    var fats: Float,
    @ColumnInfo(name = "carbohydrates")
    var carbohydrates: Float): BaseEntity(id, title, calories, ""), Serializable

package ru.kpfu.itis.fittrack.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

//TODO: решить проблему дубликатов в бд
//TODO: заполнить бд для продуктов и рецептов, но сделать такие элементы, которые нельзя удалить
@Entity
data class Product (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_product")
    override var id: Int,
    @ColumnInfo(name = "title_product")
    override var title: String,
    @ColumnInfo(name = "picture_product")
    var picture: String = "@drawable/cherry1135469",
    @ColumnInfo(name = "calories_product")
    override var calories: Int,
    @ColumnInfo(name = "proteins_product")
    var proteins: Float,
    @ColumnInfo(name = "fats_product")
    var fats: Float,
    @ColumnInfo(name = "carbohydrates_product")
    var carbohydrates: Float
): BaseEntity(id, title, calories, "", ""), Serializable
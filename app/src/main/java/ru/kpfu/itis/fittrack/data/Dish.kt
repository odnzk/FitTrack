package ru.kpfu.itis.fittrack.data

data class Dish(
    override var id: Int,
    override var title: String,
    var picture: String,
    var description: String,
    override var calories: Int,
    var proteins: Float,
    var fats: Float,
    var carbohydrates: Float
): BaseEntity(id, title, calories, "", "")

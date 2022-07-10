package ru.kpfu.itis.fittrack.data

data class Food (
    override var id: Int,
    override var title: String,
    var picture: String,
    override var calories: Int,
    var proteins: Float,
    var fats: Float,
    var carbohydrates: Float
    ): BaseEntity(id, title, calories, "", "Product")
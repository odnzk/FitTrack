package ru.kpfu.itis.fittrack.data

data class Workout(
    override var id: Int,
    override var title: String,
    override var calories: Int,
    var picture: String,
    override var category: String
): BaseEntity(id, title, calories, category, "workout")

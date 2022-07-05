package ru.kpfu.itis.fittrack.data

data class Workout(
    override var id: Int,
    override var title: String,
    override var calories: Int,
): Activity(id, title, calories)

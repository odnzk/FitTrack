package ru.kpfu.itis.fittrack.data

import java.io.Serializable

data class Training (
    override var id: Int,
    override var title: String,
    override var calories: Int,
    var picture: String,
    override var category: String,
): BaseEntity(id, title, calories, category, "Training"), Serializable
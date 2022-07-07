package ru.kpfu.itis.fittrack.listForTheDay

import android.content.Context
import android.widget.FrameLayout
import android.widget.TextView
import ru.kpfu.itis.fittrack.R

class SectionViewHolder(context: Context) : FrameLayout(context) {
    private var textViewCategory: TextView

    init {
        inflate(context, R.layout.section_view_holder, this)
        textViewCategory = findViewById(R.id.tv_Category)
    }
    fun setCategory(categoryString: String) {
        textViewCategory.text = categoryString
    }
}



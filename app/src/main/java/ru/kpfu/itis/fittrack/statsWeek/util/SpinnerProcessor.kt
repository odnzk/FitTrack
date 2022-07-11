package ru.kpfu.itis.fittrack.statsWeek.util

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner

class SpinnerProcessor(private val context: Context) {

    fun processSpinners(
        spinnerTop: Spinner,
        spinnerDown: Spinner,
        data: List<String>,
        pairIndexes: Pair<Int, Int>,
    ) {
        setSpinnerData(spinnerTop, data, pairIndexes.first)
        setSpinnerData(spinnerDown, data, pairIndexes.second)
    }

    private fun setSpinnerData(spinner: Spinner, data: List<String>, index: Int) {
        ArrayAdapter(
            context,
            android.R.layout.simple_spinner_item,
            data,
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.setSelection(index)
    }
}

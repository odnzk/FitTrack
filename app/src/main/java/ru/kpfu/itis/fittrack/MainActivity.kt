package ru.kpfu.itis.fittrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.kpfu.itis.fittrack.listForTheDay.ListForTheDayFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().add(R.id.container, ListForTheDayFragment()).commit()
    }
}
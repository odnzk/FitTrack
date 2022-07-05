package ru.kpfu.itis.fittrack

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ru.kpfu.itis.fittrack.databinding.ActivityMainBinding
import ru.kpfu.itis.fittrack.viewpager.ReceivingInformationFragment
import ru.kpfu.itis.fittrack.viewpager.ViewPagerAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val sharedPref = this.getSharedPreferences(getString(R.string.preferenceFileKey_UserData), Context.MODE_PRIVATE)
        if(sharedPref.getBoolean(ReceivingInformationFragment.IS_FIRST_TIME_RUNNING, true)){
            binding.viewPager2.adapter = ViewPagerAdapter(this, this)
            binding.viewPager2.visibility = View.VISIBLE
            sharedPref.edit().putBoolean(ReceivingInformationFragment.IS_FIRST_TIME_RUNNING, false).apply()
        }

    }
}

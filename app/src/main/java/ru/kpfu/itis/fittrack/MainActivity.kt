package ru.kpfu.itis.fittrack

import androidx.appcompat.app.AppCompatActivity
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import ru.kpfu.itis.fittrack.data.ProductViewModel
import ru.kpfu.itis.fittrack.data.RecipeViewModel
import ru.kpfu.itis.fittrack.databinding.ActivityMainBinding
import ru.kpfu.itis.fittrack.updatingdata.UpdateDailyWorker
import ru.kpfu.itis.fittrack.viewpager.ReceivingInformationFragment
import ru.kpfu.itis.fittrack.viewpager.ViewPagerAdapter
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mProductViewModel: ProductViewModel
    private lateinit var mRecipeViewModel: RecipeViewModel
    private lateinit var prefFirstLaunch: SharedPreferences
    private lateinit var controller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startUpdatingDataEveryDay()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = this.getSharedPreferences(
            getString(R.string.preferenceFileKey_UserData),
            Context.MODE_PRIVATE
        )
        if (sharedPref.getBoolean(ReceivingInformationFragment.IS_FIRST_TIME_RUNNING, true)) {
            binding.viewPager2.adapter = ViewPagerAdapter(this, this)
            binding.bottomAppBar.visibility = View.GONE
            binding.fab.visibility = View.GONE
            binding.bottomNavigationView.visibility = View.GONE
            binding.viewPager2.visibility = View.VISIBLE
            sharedPref.edit().putBoolean(ReceivingInformationFragment.IS_FIRST_TIME_RUNNING, false)
                .apply()
        }

        supportActionBar?.hide()
        with(binding) {
            controller = (supportFragmentManager.findFragmentById(R.id.container)
                    as NavHostFragment).navController
            bottomNavigationView.setupWithNavController(controller)
            bottomNavigationView.setOnItemSelectedListener {
                navigateWithOptions(it.itemId)
                true
            }
            fab.setOnClickListener {
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setPositiveButton("Food") { _, _ ->
                    controller.navigate(R.id.productsAndRecipesFragment)
                }
                builder.setNegativeButton("Workout") { _, _ ->
                    controller.navigate(R.id.workoutFragment)
                }
                builder.setTitle("Select an action")
                builder.setMessage("What do you want to add?")
                builder.create().show()
            }
        }

    }

    private fun navigateWithOptions(idToNavigateInto: Int) {
        val builder = NavOptions.Builder()
        val options = controller.currentDestination?.let {
            builder.setPopUpTo(it.id, true).build()
        }
        controller.navigate(
            idToNavigateInto,
            null,
            options,
        )
    }

    private fun startUpdatingDataEveryDay() {
        defineAndStartWork(getTimeUntilMidnight())
    }

    private fun defineAndStartWork(delay: Int) {
        val workRequest: WorkRequest =
            PeriodicWorkRequestBuilder<UpdateDailyWorker>(1, TimeUnit.DAYS)
                .setInitialDelay(delay.toLong(), TimeUnit.SECONDS)
                .build()
        WorkManager.getInstance(applicationContext).enqueue(workRequest)
    }

    private fun getTimeUntilMidnight(): Int {
        val calendar = Calendar.getInstance()
        return HOURS_IN_DAY * SEC_IN_MINUTE * SEC_IN_MINUTE - calendar.get(Calendar.HOUR_OF_DAY) * SEC_IN_MINUTE * SEC_IN_MINUTE - calendar.get(
            Calendar.MINUTE
        ) * SEC_IN_MINUTE - calendar.get(Calendar.SECOND)
    }


    companion object {
        const val FIRST_LAUNCH = "firstLaunch"
        const val KEY_FIRST_LAUNCH = "flKey"
        const val HOURS_IN_DAY = 24
        const val SEC_IN_MINUTE = 60
    }
}




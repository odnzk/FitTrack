package ru.kpfu.itis.fittrack

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import ru.kpfu.itis.fittrack.data.ProductViewModel
import ru.kpfu.itis.fittrack.data.RecipeViewModel
import ru.kpfu.itis.fittrack.databinding.ActivityMainBinding
import ru.kpfu.itis.fittrack.viewpager.ReceivingInformationFragment
import ru.kpfu.itis.fittrack.viewpager.ViewPagerAdapter
import ru.kpfu.itis.fittrack.worker.UpdateDailyWorker
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    private lateinit var mProductViewModel: ProductViewModel
    private lateinit var mRecipeViewModel: RecipeViewModel
    private lateinit var prefFirstLaunch: SharedPreferences
    private lateinit var controller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        //updating data every day (clear list and add data to ?? for stats)
        startUpdatingDataEveryDay()

        supportActionBar?.hide()
        with(binding) {
            controller = (supportFragmentManager.findFragmentById(R.id.container)
                    as NavHostFragment).navController
            bottomNavigationView.setupWithNavController(controller)

            fab.setOnClickListener {
                controller.navigate(R.id.productsAndRecipesFragment)
            }
        }

        prefFirstLaunch = getSharedPreferences(FIRST_LAUNCH, MODE_PRIVATE)
        if (prefFirstLaunch.getBoolean(KEY_FIRST_LAUNCH, true)) {
            fillFirstTime()
            prefFirstLaunch.edit().putBoolean(KEY_FIRST_LAUNCH, false).apply()
        }
    }
    private fun fillFirstTime() {
        mProductViewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        mRecipeViewModel = ViewModelProvider(this)[RecipeViewModel::class.java]
        InitialProducts.list.forEach {
            mProductViewModel.addProduct(it)
        }
        InitialRecipes.list.forEach{
            mRecipeViewModel.addRecipe(it)
        }
    }


    companion object {
        const val FIRST_LAUNCH = "firstLaunch"
        const val KEY_FIRST_LAUNCH = "flKey"
        const val HOURS_IN_DAY = 24
        const val SEC_IN_MINUTE = 60
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
}

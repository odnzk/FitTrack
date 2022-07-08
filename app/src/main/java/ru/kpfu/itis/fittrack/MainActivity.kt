package ru.kpfu.itis.fittrack

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import ru.kpfu.itis.fittrack.data.ProductViewModel
import ru.kpfu.itis.fittrack.data.RecipeViewModel
import ru.kpfu.itis.fittrack.databinding.ActivityMainBinding
import ru.kpfu.itis.fittrack.fragments.deleteFromSharedPreferences
import ru.kpfu.itis.fittrack.viewpager.ReceivingInformationFragment
import ru.kpfu.itis.fittrack.viewpager.ViewPagerAdapter

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

        val sharedPref = this.getSharedPreferences(getString(R.string.preferenceFileKey_UserData), Context.MODE_PRIVATE)
        if(sharedPref.getBoolean(ReceivingInformationFragment.IS_FIRST_TIME_RUNNING, true)){
            binding.viewPager2.adapter = ViewPagerAdapter(this, this)
            binding.bottomAppBar.visibility = View.GONE
            binding.fab.visibility = View.GONE
            binding.bottomNavigationView.visibility = View.GONE
            binding.viewPager2.visibility = View.VISIBLE
            sharedPref.edit().putBoolean(ReceivingInformationFragment.IS_FIRST_TIME_RUNNING, false).apply()
        }

        supportActionBar?.hide()
        with (binding) {
            controller = (supportFragmentManager.findFragmentById(R.id.container)
                    as NavHostFragment).navController
            bottomNavigationView.setupWithNavController(controller)
            bottomNavigationView.setOnItemSelectedListener {
                val selectedDestinationId = it.itemId
                controller.navigate(selectedDestinationId)
                controller.popBackStack(selectedDestinationId, inclusive = false)
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

    }
}

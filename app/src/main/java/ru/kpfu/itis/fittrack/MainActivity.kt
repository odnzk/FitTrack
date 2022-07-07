package ru.kpfu.itis.fittrack

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import ru.kpfu.itis.fittrack.databinding.ActivityMainBinding
import ru.kpfu.itis.fittrack.fragments.SettingsFragment
import ru.kpfu.itis.fittrack.viewpager.ReceivingInformationFragment
import ru.kpfu.itis.fittrack.viewpager.ViewPagerAdapter

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var mProductViewModel: ProductViewModel
    private lateinit var mRecipeViewModel: RecipeViewModel
    private lateinit var prefFirstLaunch: SharedPreferences
    private lateinit var controller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = this.getSharedPreferences(getString(R.string.preferenceFileKey_UserData), Context.MODE_PRIVATE)
        if(sharedPref.getBoolean(ReceivingInformationFragment.IS_FIRST_TIME_RUNNING, true)){
            binding.viewPager2.adapter = ViewPagerAdapter(this, this)
            binding.viewPager2.visibility = View.VISIBLE
            sharedPref.edit().putBoolean(ReceivingInformationFragment.IS_FIRST_TIME_RUNNING, false).apply()
        }



        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        with (binding) {
            controller = (supportFragmentManager.findFragmentById(R.id.container)
                    as NavHostFragment).navController
            bottomNavigationView.setupWithNavController(controller)

            fab.setOnClickListener {
                controller.navigate(R.id.productsAndRecipesFragment)
            }
        }

        prefFirstLaunch = getSharedPreferences( FIRST_LAUNCH, MODE_PRIVATE);
        if (prefFirstLaunch.getBoolean(KEY_FIRST_LAUNCH, true)) {
            fillFirstTime()
            prefFirstLaunch.edit().putBoolean(KEY_FIRST_LAUNCH, false).apply()
        }
    }
    private fun fillFirstTime() {
        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        mRecipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
        InitialProducts.list.forEach {
            mProductViewModel.addProduct(it)
        }
        InitialRecipes.list.forEach{
            mRecipeViewModel.addRecipe(it)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}

package ru.kpfu.itis.fittrack

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.kpfu.itis.fittrack.data.ProductViewModel
import ru.kpfu.itis.fittrack.data.RecipeViewModel
import ru.kpfu.itis.fittrack.databinding.ActivityMainBinding
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


        binding.viewPager2.adapter = ViewPagerAdapter(this, this)
        binding.bottomAppBar.visibility = View.GONE
        binding.fab.visibility = View.GONE
        binding.bottomNavigationView.visibility = View.GONE
        binding.viewPager2.visibility = View.VISIBLE
//        val sharedPref = this.getSharedPreferences(getString(R.string.preferenceFileKey_UserData), Context.MODE_PRIVATE)
//        if(sharedPref.getBoolean(ReceivingInformationFragment.IS_FIRST_TIME_RUNNING, true)){
//            binding.viewPager2.adapter = ViewPagerAdapter(this, this)
//            binding.bottomAppBar.visibility = View.GONE
//            binding.fab.visibility = View.GONE
//            binding.bottomNavigationView.visibility = View.GONE
//            binding.viewPager2.visibility = View.VISIBLE
//            sharedPref.edit().putBoolean(ReceivingInformationFragment.IS_FIRST_TIME_RUNNING, false).apply()
//        }

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
    }
}

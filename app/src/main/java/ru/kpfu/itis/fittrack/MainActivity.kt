package ru.kpfu.itis.fittrack

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.kpfu.itis.fittrack.data.ProductViewModel
import ru.kpfu.itis.fittrack.data.RecipeViewModel
import ru.kpfu.itis.fittrack.databinding.ActivityMainBinding
const val FIRST_LAUNCH = "firstLaunch";
const val KEY_FIRST_LAUNCH = "flKey"

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var mProductViewModel: ProductViewModel
    private lateinit var mRecipeViewModel: RecipeViewModel
    private lateinit var prefFirstLaunch: SharedPreferences
    private lateinit var controller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

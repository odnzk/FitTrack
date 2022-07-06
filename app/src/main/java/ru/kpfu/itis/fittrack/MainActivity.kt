package ru.kpfu.itis.fittrack

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import ru.kpfu.itis.fittrack.databinding.ActivityMainBinding
import ru.kpfu.itis.fittrack.fragments.ProductsAndRecipesFragment
import ru.kpfu.itis.fittrack.fragments.SettingsFragment

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var controller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with (binding) {
            controller = (supportFragmentManager.findFragmentById(R.id.container)
                    as NavHostFragment).navController
            bottomNavigationView.setupWithNavController(controller)

            fab.setOnClickListener {
                controller.navigate(
                    R.id.productsAndRecipesFragment
                )

                hideMenu()
            }
        }
    }

    private fun hideMenu() {
        with (binding) {
            fab.hide()
            bottomAppBar.performHide()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}

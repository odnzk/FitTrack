package ru.kpfu.itis.fittrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.kpfu.itis.fittrack.databinding.ActivityMainBinding

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
                controller.navigate(R.id.productsAndRecipesFragment)
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}

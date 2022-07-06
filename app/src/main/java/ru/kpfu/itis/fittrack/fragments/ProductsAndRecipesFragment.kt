package ru.kpfu.itis.fittrack.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.kpfu.itis.fittrack.R

class ProductsAndRecipesFragment : Fragment(R.layout.fragment_products_and_recipes) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        showMenu()
        super.onDestroyView()
    }

    private fun showMenu() {
        activity?.findViewById<FloatingActionButton>(R.id.fab)?.show()
        activity?.findViewById<BottomAppBar>(R.id.bottomAppBar)?.performShow()
    }
}

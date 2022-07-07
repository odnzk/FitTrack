package ru.kpfu.itis.fittrack.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.data.Recipe
import ru.kpfu.itis.fittrack.data.RecipeViewModel
import ru.kpfu.itis.fittrack.databinding.FragmentAddRecipeBinding

class AddRecipeFragment : Fragment(R.layout.fragment_add_recipe) {
    private var _binding: FragmentAddRecipeBinding? = null
    private val binding get() = _binding!!
    private lateinit var mRecipeViewModel: RecipeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddRecipeBinding.bind(view)
        mRecipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
        binding.addRecipeBtn.setOnClickListener {
            insertDataToDatabase()
        }
    }

    private fun insertDataToDatabase() {
        var title = ""
        var picture = ""
        var calories = 0
        var proteins = 0f
        var fats = 0f
        var carbohydrates = 0f
        var description = ""
        with(binding) {
            title = etTitle.text.toString()
            picture = etLink.text.toString()
            calories = etCalories.text.toString().toInt()
            proteins = etProteins.text.toString().toFloat()
            fats = etFats.text.toString().toFloat()
            carbohydrates = etCarbohydrates.text.toString().toFloat()
        }

        if (inputCheck(title, calories, proteins, fats, carbohydrates)) {
            // Create User Object
            val p = Recipe(
                0,
                title = title,
                picture = picture,
                calories = calories,
                proteins = proteins,
                fats = fats,
                carbohydrates = carbohydrates,
                description = description
            )
            // Add Data to Database
            mRecipeViewModel.addRecipe(p)

            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            // Navigate Back
            findNavController().navigate(R.id.action_addRecipeFragment_to_productsAndRecipesFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun inputCheck(
        title: String,
        calories: Int,
        proteins: Float,
        fats: Float,
        carbohydrates: Float
    ): Boolean {
        return title.isNotEmpty() && calories > 0 && proteins > 0 && fats > 0 && carbohydrates > 0
    }
}
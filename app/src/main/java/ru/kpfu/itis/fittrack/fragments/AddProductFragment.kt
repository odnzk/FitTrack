package ru.kpfu.itis.fittrack.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.data.Product
import ru.kpfu.itis.fittrack.data.ProductViewModel
import ru.kpfu.itis.fittrack.databinding.FragmentAddProductBinding

class AddProductFragment : Fragment(R.layout.fragment_add_product) {
    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!
    private lateinit var mProductViewModel: ProductViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddProductBinding.bind(view)
        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        binding.addProductBtn.setOnClickListener {
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
            val p = Product(
                0,
                title = title,
                picture = picture,
                calories = calories,
                proteins = proteins,
                fats = fats,
                carbohydrates = carbohydrates
            )
            // Add Data to Database
            mProductViewModel.addProduct(p)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            // Navigate Back
            findNavController().navigate(R.id.action_addFragment_to_productsAndRecipesFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG)
                .show()
        }
    }

    //TODO: нормальная проверка для продуктов
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
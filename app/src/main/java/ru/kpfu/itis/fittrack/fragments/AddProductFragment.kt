package ru.kpfu.itis.fittrack.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
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

        if (inputCheck()) {
            with(binding) {
                title = etTitle.text.toString()
                picture = etLink.text.toString()
                calories = etCalories.text.toString().toInt()
                proteins = etProteins.text.toString().toFloat()
                fats = etFats.text.toString().toFloat()
                carbohydrates = etCarbohydrates.text.toString().toFloat()
            }
            val p = Product(
                0,
                title = title,
                picture = picture,
                calories = calories,
                proteins = proteins,
                fats = fats,
                carbohydrates = carbohydrates
            )

            mProductViewModel.addProduct(p)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addFragment_to_productsAndRecipesFragment)
        }
    }

    private fun inputCheck(
    ): Boolean {
        return checkTitle() and checkCalories() and checkPFC(binding.etProteins) and checkPFC(binding.etFats) and checkPFC(binding.etCarbohydrates)
    }

    private fun checkTitle(): Boolean =  when(binding.etTitle.getText().toString().length) {
        0 -> {
            binding.etTitle.setError("Пустое поле");
            false
        }
        else -> true
    }

    private fun checkCalories(): Boolean {
        val s: String = binding.etCalories.getText().toString()
        val ans = when(s.length) {
            0 -> {
                binding.etCalories.setError("Empty field")
                false
            }
            else -> {
                when {
                    s.toInt() <= 0 -> {
                        binding.etCalories.setError("Invalid data")
                        false
                    }

                    else -> true
                }
            }
        }
        return ans
    }

    private fun checkPFC(e: TextInputEditText): Boolean {
        val s: String = e.text.toString()
        val ans = when(s.length) {
            0 -> {
                e.setError("Empty field")
                false
            }
            else -> {
                when {
                    s.toFloat() <= 0 -> {
                        e.setError("Invalid data")
                        false
                    }
                    s.toFloat() > 100 -> {
                        e.setError("It is required to specify an indicator for 100 grams of product")
                        false
                    }

                    else -> true
                }
            }
        }
        return ans
    }

}
package ru.kpfu.itis.fittrack.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.data.ProductViewModel
import ru.kpfu.itis.fittrack.data.RecipeViewModel
import ru.kpfu.itis.fittrack.databinding.FragmentProductsAndRecipesBinding

class ProductsAndRecipesFragment : Fragment(R.layout.fragment_products_and_recipes) {
    private lateinit var mProductViewModel: ProductViewModel
    private lateinit var mRecipeViewModel: RecipeViewModel
    private var _binding: FragmentProductsAndRecipesBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProductsAndRecipesBinding.bind(view)
//            TODO: добавление в список на день по долгому нажатию
        var adapter = ProductAdapter(Glide.with(this), {
            findNavController().navigate(
                R.id.action_productsAndRecipesFragment_to_productDescriptionFragment,
                ProductDescriptionFragment.createProduct(it)
            )
        })
        var recipeAdapter = RecipeAdapter(Glide.with(this), {
            findNavController().navigate(
                R.id.action_productsAndRecipesFragment_to_recipeDescriptionFragment,
                RecipeDescriptionFragment.createRecipe(it)
            )
        })

        binding.rvProduct.adapter = adapter
        binding.rvRecipe.adapter = recipeAdapter
        binding.rvProduct.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvRecipe.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        mRecipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
        mProductViewModel.getAllProducts.observe(viewLifecycleOwner, Observer { a ->
            adapter.setData(a) })
        mRecipeViewModel.getAllRecipes.observe(
            viewLifecycleOwner,
            { a -> recipeAdapter.setData(a) })

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_productsAndRecipesFragment_to_addFragment)
        }

        binding.btnAddRecipe.setOnClickListener {
            findNavController().navigate(R.id.action_productsAndRecipesFragment_to_addRecipeFragment)
        }
    }

}
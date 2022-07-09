package ru.kpfu.itis.fittrack.listForTheDay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.data.*
import ru.kpfu.itis.fittrack.databinding.FragmentListForTheDayBinding
import ru.kpfu.itis.fittrack.fragments.ProductDescriptionFragment
import ru.kpfu.itis.fittrack.fragments.RecipeDescriptionFragment


class ProductsAndRecipesForTheDayFragment : Fragment() {

    private var _binding: FragmentListForTheDayBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ListForTheDayAdapter
    private lateinit var itemSectionDecoration: ItemSectionDecoration
    private lateinit var mRecipeViewModel: RecipeViewModel
    private lateinit var mProductViewModel: ProductViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListForTheDayBinding.inflate(inflater, container, false)
        initAdapter()


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun initAdapter() {
        val list = mutableListOf<BaseEntity>()
        adapter = ListForTheDayAdapter(
            list
        ) { it ->
            // navigating to appropriate description screen from day list
            if (it is Product) {
                findNavController().navigate(
                    R.id.action_placeHolderFragment_to_productDescriptionFragment,
                    ProductDescriptionFragment.createProduct(it)
                )
            }
            if (it is Recipe) {
                findNavController().navigate(
                    R.id.action_placeHolderFragment_to_recipeDescriptionFragment2,
                    RecipeDescriptionFragment.createRecipe(it)
                )
            }
        }

        passAppropriateItemsToAdapter()
        itemSectionDecoration = ItemSectionDecoration(binding.root.context) {
            adapter.getItemList().toMutableList()
        }
        binding.rvDayList.addItemDecoration(itemSectionDecoration)
        val touchHelper = ItemTouchHelper(this.addSwipeGesture(binding, adapter, requireActivity()))
        touchHelper.attachToRecyclerView(binding.rvDayList)
        binding.rvDayList.adapter = adapter

    }


    // тут всё просто костыль, простите...
    private fun passAppropriateItemsToAdapter() {
        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        mRecipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)

        val sharedPreferencesStorage = SharedPreferencesStorage(binding.root.context)
        val arrIds = sharedPreferencesStorage.loadIDS()?.split(" ")
        val categoriesArr = sharedPreferencesStorage.loadCategories()?.split(" ")
        val typesArr = sharedPreferencesStorage.loadTypes()?.split(" ")

        mRecipeViewModel.getAllRecipes.observe(
            viewLifecycleOwner
        ) { recipes ->
            var i = 0
            if (arrIds != null) {
                for (index in arrIds) {
                    val category = categoriesArr?.get(i)
                    val type = typesArr?.get(i)
                    if (!index.isBlank()) {
                        for (recipe in recipes) {
                            if (!category.isNullOrBlank() && type == "Recipe" && recipe.id == arrIds[i].toInt()) {
                                val recipeItem = recipe.copy()
                                recipeItem.category = category
                                recipeItem.type = type
                                adapter.addItem(0, recipeItem, binding.root.context)
                            }
                        }
                    }
                    i++
                }

            }
        }


        mProductViewModel.getAllProducts.observe(
            viewLifecycleOwner
        ) { products ->
            var i = 0
            if (arrIds != null) {
                for (index in arrIds) {
                    val category = categoriesArr?.get(i)
                    val type = typesArr?.get(i)
                    if(index.isNotBlank()) {
                        for (product in products) {
                            if (!category.isNullOrBlank() && type == "Product" && product.id == arrIds[i].toInt()) {
                                val productItem = products.get(arrIds[i].toInt()).copy()
                                productItem.category = category
                                productItem.type = type
                                adapter.addItem(0, productItem, binding.root.context)
                            }
                        }
                    }
                    i++
                }
            }
        }
    }

}
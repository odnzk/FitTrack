package ru.kpfu.itis.fittrack.listForTheDay

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.work.*
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.data.*
import ru.kpfu.itis.fittrack.databinding.FragmentListForTheDayBinding
import ru.kpfu.itis.fittrack.fragments.ProductDescriptionFragment
import ru.kpfu.itis.fittrack.fragments.RecipeDescriptionFragment
import java.util.*
import java.util.*
import java.util.concurrent.TimeUnit


class ProductsAndRecipesForTheDayFragment : Fragment() {

    private var _binding: FragmentListForTheDayBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ListForTheDayAdapter
    private lateinit var itemSectionDecoration: ItemSectionDecoration
    private lateinit var mRecipeViewModel: RecipeViewModel
    private lateinit var mProductViewModel: ProductViewModel
    private var flag = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListForTheDayBinding.inflate(inflater, container, false)
        initAdapter()
        clearListNewDay()
        startWorker()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun initAdapter() {
        val list = mutableListOf<BaseEntity>()
        adapter = ListForTheDayAdapter(
            list,
            SharedPreferencesStorage(requireContext())
        ) { it ->
            // navigating to appropriate description screen from day list
            mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
            if (it is Product) {
                mProductViewModel.getAllProducts.observe(
                    viewLifecycleOwner
                ) { products ->
                    for (p in products) {
                        if (p.id == it.id) {
                            findNavController().navigate(
                                R.id.action_placeHolderFragment_to_productDescriptionFragment,
                                ProductDescriptionFragment.createProduct(p)
                            )
                        }
                    }
                }
            }
            mRecipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
            if (it is Recipe) {
                mRecipeViewModel.getAllRecipes.observe(
                    viewLifecycleOwner
                ) { recipes ->
                    for (r in recipes) {
                        if (r.id == it.id) {
                            findNavController().navigate(
                                R.id.action_placeHolderFragment_to_recipeDescriptionFragment2,
                                RecipeDescriptionFragment.createRecipe(r)
                            )
                        }
                    }
                }
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

    private fun startWorker() {
        val sp = activity?.getSharedPreferences(
            getString(R.string.preferenceFileKey_UserData),
            Context.MODE_PRIVATE
        )

        flag = sp?.getBoolean("flag", false)!!
        if (!flag) {
            val workRequest = PeriodicWorkRequestBuilder<StatWorker>(1, TimeUnit.DAYS)
                .setInitialDelay(countInitialDelay(), TimeUnit.SECONDS)
                .build()

            WorkManager
                .getInstance(requireContext())
                .enqueue(workRequest)
            sp.edit().putBoolean("flag", true).apply()
        }
    }

    private fun clearListNewDay() {
        val sp = activity?.getSharedPreferences(
            getString(R.string.preferenceFileKey_UserData),
            Context.MODE_PRIVATE
        )
        if (sp?.getBoolean(ProductDescriptionFragment.CLEAR_LIST, false) == true) {
            adapter.sharedPreferencesStorage?.clearAll()
            sp.edit()?.putBoolean(ProductDescriptionFragment.CLEAR_LIST, false)?.apply()
        }
    }

    // mock
    private fun countInitialDelay(): Long {
        val calendar = Calendar.getInstance()
        var hours = 24
        var minutes = 60
        var seconds = 60
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)
        if (minute > 0) hours--
        if (second > 0) minutes--
        return (hour * 60 * 60 + minute * 60 + second).toLong()
    }

    // тут всё просто костыль, простите...
    private fun passAppropriateItemsToAdapter() {
        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        mRecipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)

        val sharedPreferencesStorage = SharedPreferencesStorage(binding.root.context)
        val arrIds = sharedPreferencesStorage.loadIDS()?.split(" ")
        val categoriesArr = sharedPreferencesStorage.loadCategories()?.split(" ")
        val typesArr = sharedPreferencesStorage.loadTypes()?.split(" ")
        val caloriesArr = sharedPreferencesStorage.loadCalories()?.split(" ")
        val proteinsArr = sharedPreferencesStorage.loadProteins()?.split(" ")
        val fatsArr = sharedPreferencesStorage.loadFats()?.split(" ")
        val carbsArr = sharedPreferencesStorage.loadCarbs()?.split(" ")

        mRecipeViewModel.getAllRecipes.observe(
            viewLifecycleOwner
        ) { recipes ->
            var i = 0
            if (arrIds != null) {
                for (index in arrIds) {
                    val category = categoriesArr?.get(i)
                    val type = typesArr?.get(i)
                    val calories = caloriesArr?.get(i)
                    val protein = proteinsArr?.get(i)
                    val fat = fatsArr?.get(i)
                    val carb = carbsArr?.get(i)
                    if (!index.isBlank()) {
                        for (recipe in recipes) {
                            if (!calories.isNullOrBlank() && !category.isNullOrBlank() && type == "Recipe" && recipe.id == arrIds[i].toInt()
                                && !protein.isNullOrBlank() && !fat.isNullOrBlank() && !carb.isNullOrBlank()) {
                                val recipeItem = recipe.copy()
                                recipeItem.category = category
                                recipeItem.type = type
                                recipeItem.calories = calories.toInt()
                                recipeItem.proteins = protein.toFloat()
                                recipeItem.fats = fat.toFloat()
                                recipeItem.carbohydrates = carb.toFloat()
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
                    val calories = caloriesArr?.get(i)
                    val protein = proteinsArr?.get(i)
                    val fat = fatsArr?.get(i)
                    val carb = carbsArr?.get(i)
                    if(index.isNotBlank()) {
                        for (product in products) {
                            if (!calories.isNullOrBlank() && !category.isNullOrBlank() && type == "Product" && product.id == arrIds[i].toInt()
                                && !protein.isNullOrBlank() && !fat.isNullOrBlank() && !carb.isNullOrBlank()) {
                                val productItem = product.copy()
                                productItem.category = category
                                productItem.type = type
                                productItem.calories = calories.toInt()
                                productItem.proteins = protein.toFloat()
                                productItem.fats = fat.toFloat()
                                productItem.carbohydrates = carb.toFloat()
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
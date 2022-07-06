package ru.kpfu.itis.fittrack.listForTheDay

import android.content.Context
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.fittrack.data.BaseEntity
import ru.kpfu.itis.fittrack.data.Recipe
import ru.kpfu.itis.fittrack.data.RecipeViewModel
import ru.kpfu.itis.fittrack.databinding.FragmentListForTheDayBinding


class ProductsAndRecipesForTheDayFragment : Fragment() {

    private var _binding: FragmentListForTheDayBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ListForTheDayAdapter
    private lateinit var itemSectionDecoration: ItemSectionDecoration
    private lateinit var mRecipeViewModel: RecipeViewModel


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
        // this is just a temporary solution
        adapter = ListForTheDayAdapter(
            list
        ) { it ->
            //todo navigation will be here instead of Toast
            Toast.makeText(binding.root.context, it.first.title, Toast.LENGTH_LONG).show()
        }
        val sharedPreferencesStorage = SharedPreferencesStorage(binding.root.context)
        val ids = sharedPreferencesStorage.loadFood()
        val categories = sharedPreferencesStorage.loadCategories()
        mRecipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
        mRecipeViewModel.getAllRecipes.observe(
            viewLifecycleOwner
        ) { food ->
            val arrIds = ids?.split(" ")
            var categoriesArr = categories?.split(" ")
            if (arrIds != null) {
                for (i in arrIds.indices) {
                    val category = categoriesArr?.get(i)
                    if (!arrIds[i].isBlank() && !category.isNullOrBlank()) {
                        val foodItem = food.get(arrIds[i].toInt()).copy()
                        foodItem.category = category
                        adapter.addItem(0, foodItem)
                    }
                }
            }
        }
        itemSectionDecoration = ItemSectionDecoration(binding.root.context) {
            adapter.getItemList().toMutableList()
        }
        binding.rvDayList.addItemDecoration(itemSectionDecoration)
        val touchHelper = ItemTouchHelper(this.addSwipeGesture(binding, adapter))
        touchHelper.attachToRecyclerView(binding.rvDayList)
        binding.rvDayList.adapter = adapter

    }
}

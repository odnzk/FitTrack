package ru.kpfu.itis.fittrack.listForTheDay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.WorkoutRepository
import ru.kpfu.itis.fittrack.data.BaseEntity
import ru.kpfu.itis.fittrack.data.Training
import ru.kpfu.itis.fittrack.databinding.FragmentTrainingsForTheDayBinding
import ru.kpfu.itis.fittrack.fragments.WorkoutDescriptionFragment


class TrainingsForTheDayFragment : Fragment() {

    private var _binding: FragmentTrainingsForTheDayBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ListForTheDayAdapter

    private lateinit var itemSectionDecoration: ItemSectionDecoration

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrainingsForTheDayBinding.inflate(inflater, container, false)
        initAdapter()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initAdapter() {
        val list = mutableListOf<BaseEntity>()
        adapter = ListForTheDayAdapter(
            list
        ) { it ->
            if (it is Training) {
                findNavController().navigate(
                    R.id.action_placeHolderFragment_to_workoutDescriptionFragment,
                    WorkoutDescriptionFragment.create(WorkoutRepository.workoutList[it.id])
                )
            }
        }
        addItemsToAdapter()

        itemSectionDecoration = ItemSectionDecoration(binding.root.context) {
            adapter.getItemList().toMutableList()
        }
        binding.rvDayList.addItemDecoration(itemSectionDecoration)
        val touchHelper = ItemTouchHelper(this.addSwipeGesture(binding, adapter, requireActivity()))
        touchHelper.attachToRecyclerView(binding.rvDayList)
        binding.rvDayList.adapter = adapter
    }

    private fun addItemsToAdapter() {
        val workoutList = WorkoutRepository.workoutList

        val sharedPreferencesStorage = SharedPreferencesStorage(binding.root.context)
        val arrIds = sharedPreferencesStorage.loadIDS()?.split(" ")
        val categoriesArr = sharedPreferencesStorage.loadCategories()?.split(" ")
        val typesArr = sharedPreferencesStorage.loadTypes()?.split(" ")
        val caloriesArr = sharedPreferencesStorage.loadCalories()?.split(" ")

        if (arrIds != null) {
            for (i in arrIds.indices) {
                val category = categoriesArr?.get(i)
                val type = typesArr?.get(i)
                val kCal = caloriesArr?.get(i)

                if (!arrIds[i].isBlank() && !category.isNullOrBlank() && type == "Training" && !kCal.isNullOrBlank()) {
                    val trainingItem = workoutList.get(arrIds[i].toInt()).copy()
                    trainingItem.category = category
                    trainingItem.type = type
                    trainingItem.calories = kCal.toInt()
                    adapter.addItem(0, trainingItem, binding.root.context)
                }
            }
        }
    }
}
package ru.kpfu.itis.fittrack.listForTheDay

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager

import ru.kpfu.itis.fittrack.databinding.FragmentTrainingsForTheDayBinding


class TrainingsForTheDayFragment : Fragment() {

    private var _binding: FragmentTrainingsForTheDayBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ListForTheDayAdapter
    private lateinit var layoutManager: LinearLayoutManager
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

    fun initAdapter() {

        // this is just a temporary solution
        // todo comparator
        val list = someData(0, 20)
        val selector: (ItemForTheDay) -> Int = { item -> item.category.length }
        list.sortBy(selector)
        list.reverse()

        layoutManager = LinearLayoutManager(binding.root.context)
        adapter = ListForTheDayAdapter(
            list
        ) { it ->
            //todo navigation will be here instead of Toast
            Toast.makeText(binding.root.context, it.first.name, Toast.LENGTH_LONG).show()
        }

        itemSectionDecoration = ItemSectionDecoration(binding.root.context) {
            adapter.getItemList().toMutableList()
        }
        binding.rvDayList.addItemDecoration(itemSectionDecoration)
        val touchHelper = ItemTouchHelper(this.addSwipeGesture(binding, adapter))
        touchHelper.attachToRecyclerView(binding.rvDayList)
        binding.rvDayList.layoutManager = layoutManager
        binding.rvDayList.adapter = adapter
    }



    private fun someData(offset: Int, limit: Int): MutableList<ItemForTheDay> {
        val list = mutableListOf<ItemForTheDay>()
        val category = mutableListOf<String>("Aerobic", "Strength", "Stretching")
        var itemForTheDay: ItemForTheDay
        for (i in offset until offset + limit) {
            itemForTheDay = when (i) {
                in 0..5 -> {
                    val rrnd = (0..2).random()
                    val rnds = (0..400).random()
                    ItemForTheDay("Training $i", "$rnds", category[rrnd])
                }
                else -> {
                    val rrnd = (0..2).random()
                    val rnds = (0..400).random()
                    ItemForTheDay("Training $i", "$rnds", category[rrnd])
                }
            }
            list.add(itemForTheDay)
        }
        return list
    }

}
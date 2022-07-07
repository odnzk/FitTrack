package ru.kpfu.itis.fittrack.listForTheDay

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import ru.kpfu.itis.fittrack.data.BaseEntity

import ru.kpfu.itis.fittrack.databinding.FragmentTrainingsForTheDayBinding


class TrainingsForTheDayFragment : Fragment() {

    private var _binding: FragmentTrainingsForTheDayBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ListForTheDayAdapter

    private val list: MutableList<BaseEntity> = mutableListOf()


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

        adapter = ListForTheDayAdapter(
            list
        ) { it ->
            //todo navigation will be here instead of Toast


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
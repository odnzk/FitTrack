package ru.kpfu.itis.fittrack.listForTheDay

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.databinding.FragmentListForTheDayBinding
import ru.kpfu.itis.fittrack.databinding.FragmentPlaceHolderBinding


class PlaceHolderFragment : Fragment() {
    private var _binding: FragmentPlaceHolderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlaceHolderBinding.inflate(inflater, container, false)
        val fragments: ArrayList<Fragment> = arrayListOf(
            ProductsAndRecipesForTheDayFragment(),
            TrainingsForTheDayFragment()
        )
        val adapter = ViewPagerAdapter(fragments, this)
        binding.vpDayList.adapter = adapter
        binding.fragmentsIndicator.setViewPager(binding.vpDayList)
        return binding.root
    }


}
package ru.kpfu.itis.fittrack.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.kpfu.itis.fittrack.databinding.FragmentTemplateBinding

class TemplateFragment : Fragment() {
    private var _binding: FragmentTemplateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTemplateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            with(binding) {
                ivIcons.setImageResource(it.getInt(ViewPagerAdapter.IMAGE_KEY))
                tvDescription.text = it.getString(ViewPagerAdapter.DESCRIPTION_KEY)
                when (it.getInt(ViewPagerAdapter.POSITION_KEY)) {
                    0 -> rb1.isChecked = true
                    1 -> rb2.isChecked = true
                    2 -> rb3.isChecked = true
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

package ru.kpfu.itis.fittrack.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.databinding.FragmentRecipeDescriptionBinding

class RecipeDescriptionFragment : Fragment(R.layout.fragment_recipe_description) {

    private var _binding: FragmentRecipeDescriptionBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRecipeDescriptionBinding.bind(view)
        val id: Int = arguments?.getInt(ARG_TEXT)?.or(0)!!
    }

    companion object {
        private const val ARG_TEXT = "recipe"

        fun createBundle(id: Int): Bundle {
            val bundle = Bundle()
            bundle.putInt(
                ARG_TEXT,
                id
            )
            return bundle
        }
    }
}
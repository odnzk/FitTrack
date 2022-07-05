package ru.kpfu.itis.fittrack.fragments

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.data.Recipe
import ru.kpfu.itis.fittrack.data.RecipeDatabase
import ru.kpfu.itis.fittrack.databinding.FragmentRecipeDescriptionBinding

class RecipeDescriptionFragment : Fragment(R.layout.fragment_recipe_description) {
    public var r: List<Recipe> = ArrayList<Recipe>()
    var l: Recipe? = null
    private var _binding: FragmentRecipeDescriptionBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRecipeDescriptionBinding.bind(view)
        val id: Int = arguments?.getInt(ARG_TEXT)?.or(0)!!
        l = RecipeDatabase.getDatabase(requireContext()).recipeDao().loadSingle(id.toString()).value
        //binding.ivPicture.setImageURI((l?.picture)?.toUri())
        binding.tvCalories.text = "Calories: ${l?.calories}"
        binding.tvProteins.text = "Proteins: ${l?.proteins}"
//        binding.tvFats.text = "Fats: ${r?.fats}"
//        binding.tvCarbo.text = "Carbohydrates: ${r?.carbohydrates}"
        binding.tvTitle.text = l?.title
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
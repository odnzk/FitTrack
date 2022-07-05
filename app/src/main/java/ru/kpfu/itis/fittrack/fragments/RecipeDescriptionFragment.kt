package ru.kpfu.itis.fittrack.fragments

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.data.Recipe
import ru.kpfu.itis.fittrack.databinding.FragmentRecipeDescriptionBinding

class RecipeDescriptionFragment : Fragment(R.layout.fragment_recipe_description) {
    var curRecipe: Recipe? = null
    private var _binding: FragmentRecipeDescriptionBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRecipeDescriptionBinding.bind(view)
        curRecipe = (arguments?.getSerializable(ARG_TEXT) as Recipe?)
        binding.ivPicture.setImageURI((curRecipe?.picture)?.toUri())
        binding.tvCalories.text = "Calories: ${curRecipe?.calories}"
        binding.tvProteins.text = "Proteins: ${curRecipe?.proteins}"
        binding.tvFats.text = "Fats: ${curRecipe?.fats}"
        binding.tvCarbo.text = "Carbohydrates: ${curRecipe?.carbohydrates}"
        binding.tvTitle.text = curRecipe?.title
        binding.tvDescription.text = curRecipe?.description
        //TODO: вроде как картинка и описание не отображаются, хз почему, наверняка несложно исправить
        binding.btnAddItem.setOnClickListener{
            //TODO: тут будет добавление объекта на фрагмент Науруза
        }
    }

    companion object {
        private const val ARG_TEXT = "recipe"
        fun createRecipe(item: Recipe): Bundle {
            val bundle = Bundle()
            bundle.putSerializable(
                ARG_TEXT,
                item
            )
            return bundle
        }

    }
}
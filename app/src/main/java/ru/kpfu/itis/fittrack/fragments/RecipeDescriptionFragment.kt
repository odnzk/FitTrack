package ru.kpfu.itis.fittrack.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.data.Recipe
import ru.kpfu.itis.fittrack.data.RecipeViewModel
import ru.kpfu.itis.fittrack.databinding.FragmentRecipeDescriptionBinding
import ru.kpfu.itis.fittrack.listForTheDay.SharedPreferencesStorage

class RecipeDescriptionFragment : Fragment(R.layout.fragment_recipe_description) {
    var curRecipe: Recipe? = null
    lateinit var mRecipeViewModel: RecipeViewModel
    private var _binding: FragmentRecipeDescriptionBinding? = null
    private val binding get() = _binding!!
    private var category: String? = null
    private var deletedElementId: Int = 0

    //TODO: этому фрагменту требуется нормальная верстка
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
        Glide.with(this).load(curRecipe?.picture).into(binding.ivPicture)
        mRecipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)

        binding.btnDeleteItem.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setPositiveButton("Yes") { _, _ ->
                deletedElementId = curRecipe?.id ?: 0
                mRecipeViewModel.deleteRecipe(curRecipe!!)
                findNavController().navigate(R.id.action_recipeDescriptionFragment_to_productsAndRecipesFragment)
                Toast.makeText(
                    requireContext(),
                    "Successfully removed: ${curRecipe?.title}",
                    Toast.LENGTH_SHORT
                ).show()
                deleteFromSharedPreferences(deletedElementId, "Recipe", binding.root.context)
            }
            builder.setNegativeButton("No") { _, _ -> }
            builder.setTitle("Delete ${curRecipe?.title}?")
            builder.setMessage("Are you sure you want to delete ${curRecipe?.title}?")
            builder.create().show()

        }

        binding.menuCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                category = adapterView?.getItemAtPosition(position).toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                category = null
            }

        }

        //TODO: вроде как картинка и описание не отобраются, чекните, у меня эмулятор дурацкий


        binding.btnAddItem.setOnClickListener {

            val sharedPreferencesStorage = SharedPreferencesStorage(binding.root.context)
            if (category != null) {
                val type = "Recipe"
                curRecipe?.let { it1 -> Integer.valueOf(it1.id) - 1 }
                    ?.let { it2 -> sharedPreferencesStorage.addItemID(it2) }
                sharedPreferencesStorage.addCategory(category!!)
                sharedPreferencesStorage.addType(type)
            }

            Toast.makeText(
                context,
                curRecipe?.title + " has been added to the day list",
                Toast.LENGTH_SHORT
            ).show()
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
package ru.kpfu.itis.fittrack.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.bumptech.glide.Glide
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.data.*
import ru.kpfu.itis.fittrack.databinding.FragmentRecipeDescriptionBinding
import ru.kpfu.itis.fittrack.listForTheDay.SharedPreferencesStorage


class RecipeDescriptionFragment : Fragment(R.layout.fragment_recipe_description) {
    var curRecipe: Recipe? = null
    lateinit var mRecipeViewModel: RecipeViewModel
    private var _binding: FragmentRecipeDescriptionBinding? = null
    private val binding get() = _binding!!
    private lateinit var category: String
    private var deletedElementId: Int = 0

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
        Glide.with(this).load(curRecipe?.picture).into(binding.ivPicture2)
        mRecipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)

        binding.btnDeleteItem.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setPositiveButton("Yes") { _, _ ->
                deletedElementId = curRecipe?.id ?: 0
                val sharedPreferencesStorage = SharedPreferencesStorage(binding.root.context)
                val idsArr = sharedPreferencesStorage.loadIDS()?.split(" ")?.toMutableList()
                val caloriesArr = sharedPreferencesStorage.loadCalories()?.split(" ")?.toMutableList()
                val proteinsArr = sharedPreferencesStorage.loadProteins()?.split(" ")?.toMutableList()
                val fatsArr = sharedPreferencesStorage.loadFats()?.split(" ")?.toMutableList()
                val carbsArr = sharedPreferencesStorage.loadCarbs()?.split(" ")?.toMutableList()
                deleteFromSharedPreferences(deletedElementId, "Recipe", binding.root.context)
                if (idsArr != null) {
                    for (i in idsArr.indices) {
                        if (!idsArr[i].isNullOrBlank()) {
                            if (idsArr[i].toInt() == curRecipe!!.id) {
                                val calorie = caloriesArr?.get(i)
                                val protein = proteinsArr?.get(i)
                                val fat = fatsArr?.get(i)
                                val carb = carbsArr?.get(i)
                                if (calorie != null && protein != null && fat != null && carb != null) {
                                    var p = Recipe(curRecipe!!.id, curRecipe!!.title, curRecipe!!.picture, curRecipe!!.description, calorie.toInt(), protein.toFloat(), fat.toFloat(), carb.toFloat())
                                    changeSharedPref(requireActivity(), p)
                                }
                            }
                        }
                    }
                }
                Toast.makeText(
                    requireContext(),
                    "Successfully removed: ${curRecipe?.title}",
                    Toast.LENGTH_SHORT
                ).show()
                mRecipeViewModel.deleteRecipe(curRecipe!!)
                findNavController().navigate(R.id.action_recipeDescriptionFragment_to_productsAndRecipesFragment)
            }
            builder.setNegativeButton("No") { _, _ -> }
            builder.setTitle("Delete ${curRecipe?.title}?")
            builder.setMessage("Are you sure you want to delete ${curRecipe?.title}?")
            builder.create().show()

        }
        setUpAnim()

        binding.menuCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                category = adapterView?.getItemAtPosition(position).toString()

            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                category = "Breakfast"
            }

        }

        var newCalories: Int? = curRecipe?.calories
        var newProteins: Float? = curRecipe?.proteins
        var newFats: Float? = curRecipe?.fats
        var newCarbo: Float? = curRecipe?.carbohydrates


        binding.seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                newCalories = Math.round(((curRecipe?.calories!!.toFloat())*(progress.toFloat()/100)).toDouble()).toInt()
                newProteins = Math.round(((curRecipe?.proteins!!)*(progress.toFloat()/100))).toFloat()
                newFats = Math.round(((curRecipe?.fats!!)*(progress.toFloat()/100))).toFloat()
                newCarbo = Math.round(((curRecipe?.carbohydrates!!)*(progress.toFloat()/100))).toFloat()
                binding.tvCalories.text = "Calories: ${newCalories}"
                binding.tvWeight.text = "$progress grams"
                binding.tvProteins.text = "Proteins: ${newProteins}"
                binding.tvFats.text = "Fats: ${newFats}"
                binding.tvCarbo.text = "Carbs: ${newCarbo}"
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
        })

        binding.btnAddItem.setOnClickListener {
            var dish = Dish(curRecipe!!.id, curRecipe!!.title, curRecipe!!.picture, curRecipe!!.description, newCalories!!, newProteins!!, newFats!!, newCarbo!!)
            changeSharedPref(dish)
            addingValuesToSharedPreferencesExtension(binding.root.context, category, "Recipe", dish)
        }

    }

    private fun setUpAnim() {
        val slideUp: Animation =
            AnimationUtils.loadAnimation(binding.root.context, R.anim.recipe_description_fragment_slide_up_anim)
        binding.constraintLayout3.startAnimation(slideUp)

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

    private fun changeSharedPref(activity: Activity, baseEntity: BaseEntity) {
        val sharedPref = activity.getSharedPreferences(
            "UserData",
            Context.MODE_PRIVATE
        )
        var eatenCalories = sharedPref?.getInt(ProductDescriptionFragment.EATEN_CALORIES, 0)
        var eatenProteins = sharedPref?.getFloat(ProductDescriptionFragment.EATEN_PROTEINS, 0f)
        var eatenCarbs = sharedPref?.getFloat(ProductDescriptionFragment.EATEN_CARBS, 0f)
        var eatenFats = sharedPref?.getFloat(ProductDescriptionFragment.EATEN_FATS, 0f)
        var burnedCalories = sharedPref?.getInt(ProductDescriptionFragment.BURNED_CALORIES, 0)
        val editor = sharedPref?.edit()


        if (baseEntity is Product) {
            eatenCalories = eatenCalories?.minus(baseEntity.calories)
            eatenProteins = eatenProteins?.minus(baseEntity.proteins)
            eatenCarbs = eatenCarbs?.minus(baseEntity.carbohydrates)
            eatenFats = eatenFats?.minus(baseEntity.fats)
        }
        if (baseEntity is Recipe) {
            eatenCalories = eatenCalories?.minus(baseEntity.calories)
            eatenProteins = eatenProteins?.minus(baseEntity.proteins)
            eatenCarbs = eatenCarbs?.minus(baseEntity.carbohydrates)
            eatenFats = eatenFats?.minus(baseEntity.fats)
        }
        if (baseEntity is Training) {
            burnedCalories = burnedCalories?.minus(baseEntity.calories)
        }
        with(editor!!) {
            putInt(ProductDescriptionFragment.EATEN_CALORIES, eatenCalories!!)?.apply()
            putFloat(ProductDescriptionFragment.EATEN_PROTEINS, eatenProteins!!)?.apply()
            putFloat(ProductDescriptionFragment.EATEN_CARBS, eatenCarbs!!)?.apply()
            putFloat(ProductDescriptionFragment.EATEN_FATS, eatenFats!!)?.apply()
            putInt(ProductDescriptionFragment.BURNED_CALORIES, burnedCalories!!)?.apply()
        }
    }
}
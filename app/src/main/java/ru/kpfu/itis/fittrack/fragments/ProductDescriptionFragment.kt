package ru.kpfu.itis.fittrack.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import ru.kpfu.itis.fittrack.MainActivity
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.data.*
import ru.kpfu.itis.fittrack.databinding.FragmentProductDescriptionBinding
import ru.kpfu.itis.fittrack.listForTheDay.ListForTheDayAdapter
import ru.kpfu.itis.fittrack.listForTheDay.SharedPreferencesStorage

class ProductDescriptionFragment : Fragment(R.layout.fragment_product_description) {
    var curProduct: Product? = null
    lateinit var mProductViewModel: ProductViewModel
    private var _binding: FragmentProductDescriptionBinding? = null
    private val binding get() = _binding!!
    private lateinit var category: String
    private var deletedElementId: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProductDescriptionBinding.bind(view)
        curProduct = (arguments?.getSerializable(ARG_TEXT) as Product?)
        binding.ivPicture.setImageURI((curProduct?.picture)?.toUri())
        binding.tvCalories.text = "Calories: ${curProduct?.calories}"
        binding.tvProteins.text = "Proteins: ${curProduct?.proteins}"
        binding.tvFats.text = "Fats: ${curProduct?.fats}"
        binding.tvCarbo.text = "Carbs: ${curProduct?.carbohydrates}"
        binding.tvTitle.text = curProduct?.title
        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        Glide.with(this).load(curProduct?.picture).into(binding.ivPicture)
        binding.btnDeleteItem.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setPositiveButton("Yes") { _, _ ->
                deletedElementId = curProduct?.id ?: 0
                val sharedPreferencesStorage = SharedPreferencesStorage(binding.root.context)
                val idsArr = sharedPreferencesStorage.loadIDS()?.split(" ")?.toMutableList()
                val caloriesArr = sharedPreferencesStorage.loadCalories()?.split(" ")?.toMutableList()
                val proteinsArr = sharedPreferencesStorage.loadProteins()?.split(" ")?.toMutableList()
                val fatsArr = sharedPreferencesStorage.loadFats()?.split(" ")?.toMutableList()
                val carbsArr = sharedPreferencesStorage.loadCarbs()?.split(" ")?.toMutableList()
                deleteFromSharedPreferences(deletedElementId, "Product", binding.root.context)
                if (idsArr != null) {
                    for (i in idsArr.indices) {
                        if (!idsArr[i].isNullOrBlank()) {
                            if (idsArr[i].toInt() == curProduct!!.id) {
                                val calorie = caloriesArr?.get(i)
                                val protein = proteinsArr?.get(i)
                                val fat = fatsArr?.get(i)
                                val carb = carbsArr?.get(i)
                                if (calorie != null && protein != null && fat != null && carb != null) {
                                    var p = Product(curProduct!!.id, curProduct!!.title, curProduct!!.picture, calorie.toInt(), protein.toFloat(), fat.toFloat(), carb.toFloat())
                                    changeSharedPref(requireActivity(), p)
                                }
                            }
                        }
                    }
                }
                Toast.makeText(
                    requireContext(),
                    "Successfully removed: ${curProduct?.title}",
                    Toast.LENGTH_SHORT
                ).show()
                mProductViewModel.deleteProduct(curProduct!!)
                findNavController().navigate(R.id.action_productDescriptionFragment_to_productsAndRecipesFragment)
            }
            builder.setNegativeButton("No") { _, _ -> }
            builder.setTitle("Delete ${curProduct?.title}?")
            builder.setMessage("Are you sure you want to delete ${curProduct?.title}?")
            builder.create().show()

        }
        var newCalories: Int? = curProduct?.calories
        var newProteins: Float? = curProduct?.proteins
        var newFats: Float? = curProduct?.fats
        var newCarbo: Float? = curProduct?.carbohydrates
        binding.seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                newCalories = Math.round(((curProduct?.calories!!.toFloat())*(progress.toFloat()/100)).toDouble()).toInt()
                newProteins = Math.round(((curProduct?.proteins!!)*(progress.toFloat()/100))).toFloat()
                newFats = Math.round(((curProduct?.fats!!)*(progress.toFloat()/100))).toFloat()
                newCarbo = Math.round(((curProduct?.carbohydrates!!)*(progress.toFloat()/100))).toFloat()
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

        binding.btnAddItem.setOnClickListener {
            var food = Food(curProduct!!.id, curProduct!!.title, curProduct!!.picture, newCalories!!, newProteins!!, newFats!!, newCarbo!!)
            changeSharedPref(food)
            addingValuesToSharedPreferencesExtension(binding.root.context, category, "Product", food)
        }


    }

    companion object {
        private const val ARG_TEXT = "product"
        const val EATEN_CALORIES = "eaten calories"
        const val EATEN_PROTEINS = "eaten proteins"
        const val EATEN_CARBS = "eaten carbs"
        const val EATEN_FATS = "eaten fats"
        const val BURNED_CALORIES = "burned calories"
        const val CLEAR_LIST = "need to clear list"
        fun createProduct(item: Product): Bundle {
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
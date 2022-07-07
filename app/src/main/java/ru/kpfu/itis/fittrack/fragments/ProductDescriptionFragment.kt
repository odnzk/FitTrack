package ru.kpfu.itis.fittrack.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.data.Product
import ru.kpfu.itis.fittrack.data.ProductViewModel
import ru.kpfu.itis.fittrack.databinding.FragmentProductDescriptionBinding
import ru.kpfu.itis.fittrack.listForTheDay.SharedPreferencesStorage

class ProductDescriptionFragment : Fragment(R.layout.fragment_product_description) {
    var curProduct: Product? = null
    lateinit var mProductViewModel: ProductViewModel
    private var _binding: FragmentProductDescriptionBinding? = null
    private val binding get() = _binding!!
    private var category: String? = null
    private var deletedElementId: Int = 0

    //TODO: этому фрагменту требуется нормальная верстка
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProductDescriptionBinding.bind(view)
        curProduct = (arguments?.getSerializable(ARG_TEXT) as Product?)
        binding.ivPicture.setImageURI((curProduct?.picture)?.toUri())
        binding.tvCalories.text = "Calories: ${curProduct?.calories}"
        binding.tvProteins.text = "Proteins: ${curProduct?.proteins}"
        binding.tvFats.text = "Fats: ${curProduct?.fats}"
        binding.tvCarbo.text = "Carbohydrates: ${curProduct?.carbohydrates}"
        binding.tvTitle.text = curProduct?.title
        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        Glide.with(this).load(curProduct?.picture).into(binding.ivPicture)
        binding.btnDeleteItem.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setPositiveButton("Yes") { _, _ ->
                mProductViewModel.deleteProduct(curProduct!!)
                deletedElementId = curProduct?.id ?: 0
                findNavController().navigate(R.id.action_productDescriptionFragment_to_productsAndRecipesFragment)
                Toast.makeText(
                    requireContext(),
                    "Successfully removed: ${curProduct?.title}",
                    Toast.LENGTH_SHORT
                ).show()
                deleteFromSharedPreferences(deletedElementId, "Product", binding.root.context)
            }

            builder.setNegativeButton("No") { _, _ -> }
            builder.setTitle("Delete ${curProduct?.title}?")
            builder.setMessage("Are you sure you want to delete ${curProduct?.title}?")
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


        //TODO: вроде как картинка не отображается, хз почему, проверьте у себя
        binding.btnAddItem.setOnClickListener {
            changeSharedPref()
            val sharedPreferencesStorage = SharedPreferencesStorage(binding.root.context)
            if (category != null) {
                val type = "Product"
                curProduct?.let { it1 -> Integer.valueOf(it1.id) - 1 }
                    ?.let { it2 -> sharedPreferencesStorage.addItemID(it2) }
                sharedPreferencesStorage.addCategory(category!!)
                sharedPreferencesStorage.addType(type)
            }
            Toast.makeText(
                context,
                curProduct?.title + " has been added to the day list",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    private fun changeSharedPref(){
        val sharedPref = activity?.getSharedPreferences(
            getString(R.string.preferenceFileKey_UserData),
            Context.MODE_PRIVATE
        )
        var eatenCalories = sharedPref?.getInt(EATEN_CALORIES, 0)
        var eatenProteins = sharedPref?.getFloat(EATEN_PROTEINS, 0f)
        var eatenCarbs = sharedPref?.getFloat(EATEN_CARBS, 0f)
        var eatenFats = sharedPref?.getFloat(EATEN_FATS, 0f)
        val editor = sharedPref?.edit()

        eatenCalories = eatenCalories?.plus(curProduct?.calories!!)
        eatenProteins = eatenProteins?.plus(curProduct?.proteins!!)
        eatenCarbs = eatenCarbs?.plus(curProduct?.carbohydrates!!)
        eatenFats = eatenFats?.plus(curProduct?.fats!!)

        editor?.putInt(EATEN_CALORIES, eatenCalories!!)?.apply()
        editor?.putFloat(EATEN_PROTEINS, eatenProteins!!)?.apply()
        editor?.putFloat(EATEN_CARBS, eatenCarbs!!)?.apply()
        editor?.putFloat(EATEN_FATS, eatenFats!!)?.apply()
    }
    companion object {
        private const val ARG_TEXT = "product"
        const val EATEN_CALORIES = "eaten calories"
        const val EATEN_PROTEINS = "eaten proteins"
        const val EATEN_CARBS = "eaten carbs"
        const val EATEN_FATS = "eaten fats"
        fun createProduct(item: Product): Bundle {
            val bundle = Bundle()
            bundle.putSerializable(
                ARG_TEXT,
                item
            )
            return bundle
        }

    }
}
package ru.kpfu.itis.fittrack.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.data.Product
import ru.kpfu.itis.fittrack.data.ProductViewModel
import ru.kpfu.itis.fittrack.data.RecipeViewModel
import ru.kpfu.itis.fittrack.databinding.FragmentProductDescriptionBinding

class ProductDescriptionFragment : Fragment(R.layout.fragment_product_description) {
    var curProduct: Product? = null
    lateinit var mProductViewModel: ProductViewModel
    private var _binding: FragmentProductDescriptionBinding? = null
    private val binding get() = _binding!!
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
        binding.btnDeleteItem.setOnClickListener{
            val builder = AlertDialog.Builder(requireContext())
            builder.setPositiveButton("Yes") { _, _ ->
                mProductViewModel.deleteProduct(curProduct!!)
                findNavController().navigate(R.id.action_productDescriptionFragment_to_productsAndRecipesFragment)
                Toast.makeText(
                    requireContext(),
                    "Successfully removed: ${curProduct?.title}",
                    Toast.LENGTH_SHORT).show()
            }

            builder.setNegativeButton("No") { _, _ -> }
            builder.setTitle("Delete ${curProduct?.title}?")
            builder.setMessage("Are you sure you want to delete ${curProduct?.title}?")
            builder.create().show()

        }

        //TODO: вроде как картинка не отображается, хз почему, проверьте у себя
        binding.btnAddItem.setOnClickListener {
            //TODO: тут будет добавление объекта на фрагмент Науруза
        }


    }

    companion object {
        private const val ARG_TEXT = "product"
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
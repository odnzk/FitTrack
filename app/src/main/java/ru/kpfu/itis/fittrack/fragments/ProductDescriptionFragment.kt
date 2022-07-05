package ru.kpfu.itis.fittrack.fragments

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.data.Product
import ru.kpfu.itis.fittrack.databinding.FragmentProductDescriptionBinding

class ProductDescriptionFragment : Fragment(R.layout.fragment_product_description) {
    var curProduct: Product? = null
    private var _binding: FragmentProductDescriptionBinding? = null
    private val binding get() = _binding!!

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
        //TODO: вроде как картинка не отображается, хз почему, наверняка несложно исправить
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
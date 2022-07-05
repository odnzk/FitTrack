package ru.kpfu.itis.fittrack.fragments

import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.data.Product
import ru.kpfu.itis.fittrack.databinding.ItemProductBinding

class ProductViewHolder(
    var binding: ItemProductBinding,
    private var glide: RequestManager,
    private var onItemClick: (Product) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(a: Product) {
        with(binding) {
            binding.ivProduct.setImageURI(a.picture.toUri())
            binding.tvProductName.text = a.title
            root.setOnClickListener {
                onItemClick(a)
            }

            glide
                .load(a.picture)
                .placeholder(R.drawable.cherry1135469)
                .error(R.drawable.cherry1135469)
                .into(ivProduct)
        }
    }
}
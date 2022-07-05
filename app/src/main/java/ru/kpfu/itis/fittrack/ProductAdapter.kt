package ru.kpfu.itis.fittrack

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ru.kpfu.itis.fittrack.data.Product
import ru.kpfu.itis.fittrack.databinding.ItemProductBinding

class ProductAdapter(
    private val glide: RequestManager,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductViewHolder>() {

    private var productList = emptyList<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            binding = ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            glide = glide,
            onItemClick = onItemClick
        )
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = productList[position]
        holder.onBind(currentItem)
    }

    fun setData(p: List<Product>) {
        this.productList = p
        notifyDataSetChanged()
    }
}
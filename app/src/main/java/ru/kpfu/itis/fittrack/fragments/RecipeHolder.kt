package ru.kpfu.itis.fittrack.fragments

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.data.Product
import ru.kpfu.itis.fittrack.data.Recipe
import ru.kpfu.itis.fittrack.databinding.ItemRecipeBinding

class RecipeHolder  (
    var binding: ItemRecipeBinding,
    private var glide: RequestManager,
    private var onItemClick: (Recipe) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(a: Recipe) {
        with(binding) {
            root.setOnClickListener {
                onItemClick(a)
            }

            glide
                .load(a.picture)
                .placeholder(R.drawable.pizza)
                .error(R.drawable.pizza)
                .into(ivRecipe)
        }
    }
}
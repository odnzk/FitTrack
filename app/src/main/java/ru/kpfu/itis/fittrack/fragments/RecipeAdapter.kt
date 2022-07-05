package ru.kpfu.itis.fittrack.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ru.kpfu.itis.fittrack.data.Product
import ru.kpfu.itis.fittrack.data.Recipe
import ru.kpfu.itis.fittrack.databinding.ItemProductBinding
import ru.kpfu.itis.fittrack.databinding.ItemRecipeBinding

class RecipeAdapter (
    private val glide: RequestManager,
    private val onItemClick: (Recipe) -> Unit
    ) : RecyclerView.Adapter<RecipeHolder>() {

        private var recipeList = emptyList<Recipe>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeHolder {
            return RecipeHolder(
                binding = ItemRecipeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                glide = glide,
                onItemClick = onItemClick
            )
        }

        override fun getItemCount(): Int {
            return recipeList.size
        }

        override fun onBindViewHolder(holder: RecipeHolder, position: Int) {
            val currentItem = recipeList[position]
            holder.binding.tvRecipe
                .text = currentItem.title
            holder.binding.ivRecipe.setImageURI(currentItem.picture.toUri())
        }

        fun setData(p: List<Recipe>) {
            this.recipeList = p
            notifyDataSetChanged()
        }
}
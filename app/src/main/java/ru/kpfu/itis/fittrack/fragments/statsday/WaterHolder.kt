package ru.kpfu.itis.fittrack.fragments.statsday

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.databinding.ItemWaterBinding

class WaterHolder(
    private val binding: ItemWaterBinding,
    private val glide: RequestManager,
    private val lastIndex: Int,
    private val onItemClick: (Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(int: Int) {
        if (int == lastIndex) {
            glide
                .load(R.drawable.drink_water)
                .into(binding.ivImage)
            binding.ivImage.setOnClickListener {
                onItemClick(int)
            }
        }
    }
}
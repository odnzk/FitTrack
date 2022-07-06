package ru.kpfu.itis.fittrack.fragments.statsday

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ru.kpfu.itis.fittrack.databinding.ItemWaterBinding

class WaterAdapter(
    private val list: List<Int>,
    private val glide: RequestManager,
    private val onItemClick: (Int) -> Unit,
) : RecyclerView.Adapter<WaterHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WaterHolder = WaterHolder(
        ItemWaterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        ),
        glide = glide,
        list.lastIndex,
        onItemClick,
    )

    override fun onBindViewHolder(
        holder: WaterHolder,
        position: Int
    ) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size
}
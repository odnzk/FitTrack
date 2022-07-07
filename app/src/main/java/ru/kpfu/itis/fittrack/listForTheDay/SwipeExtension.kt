package ru.kpfu.itis.fittrack.listForTheDay

import android.widget.Adapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

fun Fragment.addSwipeGesture(
    binding: ViewBinding,
    adapter: ListForTheDayAdapter
): SwipeGesture {
    val swipeGesture = object : SwipeGesture(binding.root.context) {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            when (direction) {
                ItemTouchHelper.LEFT -> {
                    context?.let { adapter.deleteItem(viewHolder.bindingAdapterPosition, it) }
                }
                ItemTouchHelper.RIGHT -> {
                    context?.let { adapter.deleteItem(viewHolder.bindingAdapterPosition, it) }
                }
            }
        }
    }
    return swipeGesture

}
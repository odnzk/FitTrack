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
                    adapter.deleteItem(viewHolder.bindingAdapterPosition)
                }
                ItemTouchHelper.RIGHT -> {
                    adapter.deleteItem(viewHolder.bindingAdapterPosition)
                }
            }
        }
    }
    return swipeGesture

}
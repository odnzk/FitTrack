package ru.kpfu.itis.fittrack.listForTheDay

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    val items: ArrayList<Fragment>,
    fragment: Fragment
):FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        return items[position]
    }
}
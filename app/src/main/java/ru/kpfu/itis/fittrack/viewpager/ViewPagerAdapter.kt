package ru.kpfu.itis.fittrack.viewpager

import android.content.Context
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.kpfu.itis.fittrack.R

class ViewPagerAdapter(fa: FragmentActivity, context: Context) : FragmentStateAdapter(fa) {
    private val descriptionList = context.resources.getStringArray(R.array.vp_descriptions)
    private val imageList =
        arrayListOf(R.drawable.vp_icon1, R.drawable.vp_icon2, R.drawable.vp_icon3)

    override fun getItemCount() = descriptionList.size + 1
    override fun createFragment(position: Int): Fragment {
        if (position < descriptionList.size)
            return TemplateFragment().apply {
                arguments = bundleOf(
                    DESCRIPTION_KEY to descriptionList[position],
                    IMAGE_KEY to imageList[position],
                    POSITION_KEY to position
                )
            }
        return ReceivingInformationFragment()
    }

    companion object BundleKeys {
        const val DESCRIPTION_KEY = "vp_description"
        const val IMAGE_KEY = "vp_image"
        const val POSITION_KEY = "vp_position"
    }
}

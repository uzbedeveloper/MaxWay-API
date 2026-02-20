package uz.group1.maxwayapp.presentation.screens.main.banner.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.group1.maxwayapp.data.model.BannerUIData
import uz.group1.maxwayapp.presentation.screens.main.banner.page.BannerPage
class BannerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private var list: List<BannerUIData> = emptyList()

    fun submitList(newList: List<BannerUIData>) {
        list = newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment {
        return BannerPage.newInstance(list[position])
    }
}

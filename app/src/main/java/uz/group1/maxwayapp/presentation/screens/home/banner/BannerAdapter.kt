package uz.group1.maxwayapp.presentation.screens.home.banner

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.group1.maxwayapp.data.model.BannerUIData

class BannerAdapter(fragment: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragment, lifecycle) {
    private var list: List<BannerUIData> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<BannerUIData>){
        list = newList
        notifyDataSetChanged()
    }
    override fun createFragment(position: Int): Fragment {
        val newPosition = position % list.size
        return BannerPage.newInstance(list[newPosition])
    }

    override fun getItemCount(): Int {
        return if (list.isEmpty()) 0 else Int.MAX_VALUE
    }
}
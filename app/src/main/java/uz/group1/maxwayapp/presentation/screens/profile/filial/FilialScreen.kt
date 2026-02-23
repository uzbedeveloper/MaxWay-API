package uz.group1.maxwayapp.presentation.screens.profile.filial

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.databinding.ScreenFilialBinding
import uz.group1.maxwayapp.presentation.screens.profile.filial.list.ListScreen
import uz.group1.maxwayapp.presentation.screens.profile.filial.map.MapScreen

class FilialScreen : Fragment(R.layout.screen_filial) {
    private val binding by viewBinding(ScreenFilialBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupViewPager() {
        val adapter = FilialPagerAdapter(this)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = if (position == 0) "Список" else "Карта"
        }.attach()
    }
    private class FilialPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> ListScreen()
                else -> MapScreen()
            }
        }
    }
}
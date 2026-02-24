package uz.group1.maxwayapp.presentation.screens.cart

import android.graphics.Color
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.databinding.ScreenCartBinding
import uz.group1.maxwayapp.presentation.screens.cart.adapter.ScreenSlidePagerAdapter

class CartScreen : Fragment(R.layout.screen_cart) {

    private val binding by viewBinding(ScreenCartBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ScreenSlidePagerAdapter(this)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = if (position == 0) "Текущий заказ" else "История"
        }.attach()

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
    }
}
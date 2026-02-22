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
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.databinding.ScreenCartBinding
import uz.group1.maxwayapp.presentation.screens.cart.adapter.ScreenSlidePagerAdapter

class CartScreen : Fragment(R.layout.screen_cart) {

    private val binding by viewBinding(ScreenCartBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ScreenSlidePagerAdapter(this)
        binding.viewPager.adapter = adapter

        binding.cardItem1.setOnClickListener {
            binding.viewPager.setCurrentItem(0, true)
        }
        binding.cardItem2.setOnClickListener {
            binding.viewPager.setCurrentItem(1, true)
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateToggleUI(isListActive = position == 0)
            }
        })

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
    }

    private fun updateToggleUI(isListActive: Boolean) {
        TransitionManager.beginDelayedTransition(binding.root as ViewGroup)

        binding.cardItem1.apply {
            setCardBackgroundColor(if (isListActive) Color.WHITE else Color.TRANSPARENT)
            cardElevation = if (isListActive) 4f else 0f
        }
        binding.cardItem2.apply {
            setCardBackgroundColor(if (isListActive) Color.TRANSPARENT else Color.WHITE)
            cardElevation = if (isListActive) 0f else 4f
        }
    }
}
package uz.group1.maxwayapp.presentation.screens.cart

import android.graphics.Color
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.data.repository_impl.ProductRepositoryImpl
import uz.group1.maxwayapp.databinding.ScreenCartBinding
import uz.group1.maxwayapp.presentation.screens.base_fragment.BaseFragment
import uz.group1.maxwayapp.presentation.screens.cart.adapter.ScreenSlidePagerAdapter
import uz.group1.maxwayapp.utils.NotificationType
import uz.group1.maxwayapp.utils.showNotification

class CartScreen : BaseFragment(R.layout.screen_cart) {

    private val binding by viewBinding(ScreenCartBinding::bind)
    private val repository = ProductRepositoryImpl.getInstance()

    override val applyBottomInset = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ScreenSlidePagerAdapter(this)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = if (position == 0) "Текущий заказ" else "История"
        }.attach()

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack(R.id.homeScreen, false)
        }

        /*
        * o'zi aslida repository->useCase->ViewModel->UI bo'lishi kerak edi.
        * keyinchalik implementation ni to'g'rilab ketaman.
        * xozircha repository->UI
        * */

        if (!repository.hasToken()){
            requireActivity().showNotification("Siz ro'yhatdan o'tishingiz lozim", NotificationType.WARNING)
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.homeScreen, false)
                .build()

            findNavController().navigate(R.id.registerScreen, null, navOptions)
        }
    }
}
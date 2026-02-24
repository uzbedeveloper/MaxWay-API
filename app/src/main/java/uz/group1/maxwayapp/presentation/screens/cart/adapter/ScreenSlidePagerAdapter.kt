package uz.group1.maxwayapp.presentation.screens.cart.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.group1.maxwayapp.presentation.screens.cart.pages.current_orders.CurrentOrdersPage
import uz.group1.maxwayapp.presentation.screens.cart.pages.history_order.HistoryOrdersPage

class ScreenSlidePagerAdapter(fa: Fragment) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment {
        return if (position == 0) CurrentOrdersPage() else HistoryOrdersPage()
    }
}
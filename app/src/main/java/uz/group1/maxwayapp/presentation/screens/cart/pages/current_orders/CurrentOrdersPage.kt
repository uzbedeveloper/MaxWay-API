package uz.group1.maxwayapp.presentation.screens.cart.pages.current_orders

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.databinding.PageCurrentOrdersBinding
import uz.group1.maxwayapp.presentation.screens.cart.adapter.MyOrderAdapter

class CurrentOrdersPage: Fragment(R.layout.page_current_orders) {

    private val binding by viewBinding(PageCurrentOrdersBinding::bind)
    private val viewModel: CurrentOrdersPageContract by viewModels<CurrentOrdersPageViewModel>{
        CurrentOrdersPageViewModelFactory()
    }

    private val adapter by lazy { MyOrderAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ordersRv.adapter = adapter

        viewModel.ordersLiveData.observe(viewLifecycleOwner) { orders ->
            if (orders.isEmpty()){
                binding.emptyState.isVisible=true
                binding.layoutMain.isVisible=false
            }else{
                binding.emptyState.isVisible=false
                binding.layoutMain.isVisible=true
                adapter.submitList(orders)
            }
        }

    }
}
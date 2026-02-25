package uz.group1.maxwayapp.presentation.screens.cart.pages.history_order

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.databinding.PageCurrentOrdersBinding
import uz.group1.maxwayapp.presentation.screens.cart.adapter.MyOrderAdapter
import uz.group1.maxwayapp.presentation.screens.cart.pages.current_orders.CurrentOrdersPageContract
import uz.group1.maxwayapp.presentation.screens.cart.pages.current_orders.CurrentOrdersPageViewModel
import uz.group1.maxwayapp.presentation.screens.cart.pages.current_orders.CurrentOrdersPageViewModelFactory
import uz.group1.maxwayapp.utils.NotificationType
import uz.group1.maxwayapp.utils.showNotification
import kotlin.getValue

class HistoryOrdersPage: Fragment(R.layout.page_current_orders) {

    private val binding by viewBinding(PageCurrentOrdersBinding::bind)
    private val viewModel: HistoryOrdersPageContract by viewModels<HistoryOrdersPageViewModel>{
        HistoryOrdersPageViewModelFactory()
    }

    private val adapter by lazy { MyOrderAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ordersRv.adapter = adapter

        viewModel.progressLiveData.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.shimmerLayout.startShimmer()
                binding.shimmerLayout.visibility = View.VISIBLE
                binding.layoutMain.visibility = View.GONE
                binding.emptyState.visibility = View.GONE
            } else {
                binding.shimmerLayout.stopShimmer()
                binding.shimmerLayout.visibility = View.GONE
                binding.layoutMain.visibility = View.VISIBLE
            }
        }

        viewModel.ordersLiveData.observe(viewLifecycleOwner) { orders ->
            Log.d("TTT", "onViewCreated: ${orders.size}")
            if (orders.isEmpty()) {
                binding.emptyState.visibility = View.VISIBLE
                binding.layoutMain.visibility = View.GONE
            } else {
                binding.emptyState.visibility = View.GONE
                binding.layoutMain.visibility = View.VISIBLE
                adapter.submitList(orders)
            }
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) { errorMessage ->
            requireActivity().showNotification(errorMessage, NotificationType.ERROR)
        }

        viewModel.loadMyOrders()

    }
}
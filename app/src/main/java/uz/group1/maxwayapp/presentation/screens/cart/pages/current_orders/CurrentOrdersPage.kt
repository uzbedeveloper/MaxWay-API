package uz.group1.maxwayapp.presentation.screens.cart.pages.current_orders

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.data.sources.local.TokenManager
import uz.group1.maxwayapp.databinding.PageCurrentOrdersBinding
import uz.group1.maxwayapp.presentation.screens.cart.adapter.MyOrderAdapter
import uz.group1.maxwayapp.utils.NotificationType
import uz.group1.maxwayapp.utils.showNotification

class CurrentOrdersPage: Fragment(R.layout.page_current_orders) {

    private val binding by viewBinding(PageCurrentOrdersBinding::bind)
    private val viewModel: CurrentOrdersPageContract by viewModels<CurrentOrdersPageViewModel>{
        CurrentOrdersPageViewModelFactory()
    }

    private val adapter by lazy { MyOrderAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ordersRv.adapter = adapter

        adapter.setOnItemClickListener {
            findNavController().navigate(R.id.orderDetailScreen, bundleOf("currentOrder" to it))
        }

        binding.btnHome.setOnClickListener {
            findNavController().navigate(R.id.homeScreen)
        }

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
            if (TokenManager.getToken() == null){
                binding.emptyState.visibility = View.VISIBLE
                binding.layoutMain.visibility = View.GONE
                return@observe
            }

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
            when{
                errorMessage.contains("400")->{
                    requireActivity().showNotification("Foydalanuvchi topilmadi", NotificationType.ERROR)
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(R.id.homeScreen, false)
                        .build()

                    findNavController().navigate(R.id.registerScreen, null, navOptions)
                }

                else->{
                    requireActivity().showNotification(errorMessage, NotificationType.ERROR)
                }
            }
        }

        viewModel.loadMyOrders()

    }
}


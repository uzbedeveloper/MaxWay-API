package uz.group1.maxwayapp.presentation.screens.cart.pages.current_orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.group1.maxwayapp.data.repository_impl.ProductRepositoryImpl
import uz.group1.maxwayapp.domain.usecase.impl.GetMyOrdersUseCaseImpl

class CurrentOrdersPageViewModelFactory: ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CurrentOrdersPageViewModel(GetMyOrdersUseCaseImpl(ProductRepositoryImpl.getInstance())) as T
    }
}
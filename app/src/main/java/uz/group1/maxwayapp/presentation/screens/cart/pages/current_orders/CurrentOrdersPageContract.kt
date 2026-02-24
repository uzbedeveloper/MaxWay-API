package uz.group1.maxwayapp.presentation.screens.cart.pages.current_orders

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.StateFlow
import uz.group1.maxwayapp.data.model.CategoryUIData
import uz.group1.maxwayapp.data.model.MyOrdersUIData

interface CurrentOrdersPageContract {

    val ordersLiveData: LiveData<List<MyOrdersUIData>>
    val errorLiveData: LiveData<String>
    val progressLiveData: LiveData<Boolean>

    fun loadMyOrders()
}
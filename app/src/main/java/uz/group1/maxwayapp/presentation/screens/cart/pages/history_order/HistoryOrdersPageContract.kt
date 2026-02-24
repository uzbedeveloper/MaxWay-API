package uz.group1.maxwayapp.presentation.screens.cart.pages.history_order

import androidx.lifecycle.LiveData
import uz.group1.maxwayapp.data.model.MyOrdersUIData

interface HistoryOrdersPageContract {

    val ordersLiveData: LiveData<List<MyOrdersUIData>>
    val errorLiveData: LiveData<String>
    val progressLiveData: LiveData<Boolean>

    fun loadMyOrders()
}
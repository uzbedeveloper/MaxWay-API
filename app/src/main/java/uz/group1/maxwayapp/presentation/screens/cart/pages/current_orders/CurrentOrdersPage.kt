package uz.group1.maxwayapp.presentation.screens.cart.pages.current_orders

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.databinding.PageCurrentOrdersBinding

class CurrentOrdersPage: Fragment(R.layout.page_current_orders) {

    private val binding by viewBinding(PageCurrentOrdersBinding::bind)
    private val viewModel: CurrentOrdersPageContract by viewModels<CurrentOrdersPageViewModel>{
        CurrentOrdersPageViewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }
}
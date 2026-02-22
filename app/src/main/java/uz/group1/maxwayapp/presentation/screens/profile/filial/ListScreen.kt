package uz.group1.maxwayapp.presentation.screens.profile.filial

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.databinding.ItemListBinding

class ListScreen: Fragment(R.layout.item_list) {
    private val binding by viewBinding(ItemListBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
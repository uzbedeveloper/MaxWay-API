package uz.group1.maxwayapp.presentation.screens.profile.filial

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.databinding.ScreenFilialBinding

class FilialScreen: Fragment(R.layout.screen_filial) {
    private val binding by viewBinding(ScreenFilialBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            replaceFragment(MapScreen())
        }

        binding.cardItem2.setOnClickListener {
            replaceFragment(MapScreen())
            updateTabUI(isMap = true)
        }

        binding.cardItem1.setOnClickListener {
            replaceFragment(ListScreen())
            updateTabUI(isMap = false)
        }

        binding.btnBack.setOnClickListener {
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun updateTabUI(isMap: Boolean) {
        if (isMap) {
            binding.cardItem2.setCardBackgroundColor(resources.getColor(R.color.white, null))
            binding.cardItem1.setCardBackgroundColor(android.graphics.Color.TRANSPARENT)
        } else {
            binding.cardItem1.setCardBackgroundColor(resources.getColor(R.color.white, null))
            binding.cardItem2.setCardBackgroundColor(android.graphics.Color.TRANSPARENT)
        }
    }
}
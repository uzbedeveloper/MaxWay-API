package uz.group1.maxwayapp.presentation.screens.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.databinding.ScreenMainBinding
import uz.group1.maxwayapp.presentation.screens.home.cart_bottomsheet.CartBottomSheet
import kotlin.getValue

class MainScreen: Fragment(R.layout.screen_main) {

    private val binding by viewBinding(ScreenMainBinding::bind)
    private val viewModel: MainViewModel by viewModels{ MainViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = childFragmentManager
            .findFragmentById(R.id.navContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNav.setupWithNavController(navController)

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.cartDialog -> {
                    val bottomSheet = CartBottomSheet()
                    bottomSheet.show(childFragmentManager, "CustomBottomSheet")
                    false
                }
                else -> {
                    NavigationUI.onNavDestinationSelected(item, navController)
                    true
                }
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val isHidden = destination.id in setOf(
                R.id.orderDetailScreen,
                R.id.searchScreen,
                R.id.notificationScreen,
                R.id.storiesScreen,
                )
            binding.bottomNav.isVisible = !isHidden
        }

        setUpObservers()
    }

    private fun setUpObservers() {
        lifecycleScope.launch {
            viewModel.totalCartCount.collect { count ->
                updateCartBadge(count)
            }
        }
    }


    private fun updateCartBadge(count: Int) {
        val bottomNav = binding.bottomNav

        if (count > 0) {
            val badge = bottomNav.getOrCreateBadge(R.id.cartDialog)
            badge.isVisible = true
            badge.number = count
            badge.backgroundColor = Color.RED
            badge.badgeTextColor = Color.WHITE
        } else {
            bottomNav.removeBadge(R.id.cartDialog)
        }
    }

}
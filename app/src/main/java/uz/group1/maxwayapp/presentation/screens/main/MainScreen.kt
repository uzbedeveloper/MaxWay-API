package uz.group1.maxwayapp.presentation.screens.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.databinding.ScreenMainBinding
import uz.group1.maxwayapp.presentation.screens.main.banner.adapter.BannerAdapter

class MainScreen: Fragment(R.layout.screen_main) {
    private val binding by viewBinding(ScreenMainBinding::bind)
    private val viewModel: MainViewModel by viewModels<MainViewModelImpl> { MainViewModelFactory() }
    private lateinit var bannerAdapter: BannerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bannerAdapter = BannerAdapter(childFragmentManager, lifecycle)
        binding.viewPager.adapter = bannerAdapter

        observe()
        viewModel.loadBanners()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.banners.collect {
                bannerAdapter.submitList(it)
            }
        }
    }
}
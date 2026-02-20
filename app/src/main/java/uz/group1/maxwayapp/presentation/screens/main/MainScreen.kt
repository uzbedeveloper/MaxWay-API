package uz.group1.maxwayapp.presentation.screens.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.databinding.ScreenMainBinding
import uz.group1.maxwayapp.presentation.screens.main.banner.adapter.BannerAdapter
import uz.group1.maxwayapp.presentation.screens.main.banner.adapter.CategoryAdapter

class MainScreen: Fragment(R.layout.screen_main) {
    private val binding by viewBinding(ScreenMainBinding::bind)
    private val viewModel: MainViewModel by viewModels<MainViewModelImpl> { MainViewModelFactory() }
    private lateinit var bannerAdapter: BannerAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private var autoScJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bannerAdapter = BannerAdapter(childFragmentManager, lifecycle)
        binding.viewPager.adapter = bannerAdapter

        categoryAdapter = CategoryAdapter()
        binding.categorysRecyclerView.adapter = categoryAdapter

        observe()
        viewModel.loadBanners()
        viewModel.loadCategoriesWithProducts()
    }


    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.banners.collect { item ->
                        if (item.isNotEmpty()){
                            bannerAdapter.submitList(item)
                            val newSize = (Int.MAX_VALUE / 2) - ((Int.MAX_VALUE / 2) % item.size)
                            binding.viewPager.setCurrentItem(newSize, false)
                            startAutoScrooll()
                        }
                    }
                }

                launch {
                    viewModel.categories.collect { list ->
                        categoryAdapter.submitList(list)
                    }
                }
            }
        }
    }

    private fun startAutoScrooll(){
        autoScJob?.cancel()
        autoScJob = viewLifecycleOwner.lifecycleScope.launch {
            while (true){
                delay(5000)
                binding.viewPager.currentItem += 1
            }
        }
    }

    override fun onStop() {
        super.onStop()
        autoScJob?.cancel()
    }
}
package uz.group1.maxwayapp.presentation.screens.home

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
import uz.group1.maxwayapp.databinding.ScreenHomeBinding
import uz.group1.maxwayapp.presentation.screens.home.adapter.CategoryAdapter
import uz.group1.maxwayapp.presentation.screens.home.adapter.StoriesAdapter
import uz.group1.maxwayapp.presentation.screens.home.banner.BannerAdapter
import uz.group1.maxwayapp.presentation.screens.main.banner.adapter.ProductsAdapter

class HomeScreen: Fragment(R.layout.screen_home) {
    private val binding by viewBinding(ScreenHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels<HomeViewModelImpl> { HomeViewModelFactory() }
    private lateinit var bannerAdapter: BannerAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var productsAdapter: ProductsAdapter
    private var autoScJob: Job? = null
    private lateinit var adapter: StoriesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bannerAdapter = BannerAdapter(childFragmentManager, lifecycle)
        binding.viewPager.adapter = bannerAdapter

        categoryAdapter = CategoryAdapter()
        binding.categoriesRecyclerView.adapter = categoryAdapter
        productsAdapter = ProductsAdapter()
        binding.productsRecyclerVew.adapter = productsAdapter

        categoryAdapter.setOnItemClickListener {
            viewModel.selectedCategory(it.id)
        }

        adapter = StoriesAdapter()
        binding.storiesRv.adapter = adapter

        observe()
        viewModel.loadHome()
    }
    private fun observe(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.banners.collect { item->
                        if (item.isNotEmpty()){
                            bannerAdapter.submitList(item)
                            val newSize = (Int.MAX_VALUE / 2) - ((Int.MAX_VALUE/2) % item.size)
                            binding.viewPager.setCurrentItem(newSize, false)
                            startAutoScrooll()
                        }
                    }
                }
                launch {
                    viewModel.categorys.collect {
                        if (it.isNotEmpty()){
                            categoryAdapter.submitList(it)
                        }
                    }
                }
                launch {
                    viewModel.menu.collect {
                        if(it.isNotEmpty()){
                            productsAdapter.submitList(it)
                        }
                    }
                }
                launch {
                    viewModel.storiesLiveData.observe(viewLifecycleOwner){
                        if (it.isNotEmpty()){
                            adapter.submitList(it)
                        }
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

}
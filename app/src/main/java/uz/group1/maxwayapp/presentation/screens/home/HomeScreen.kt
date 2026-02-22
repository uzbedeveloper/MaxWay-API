package uz.group1.maxwayapp.presentation.screens.home

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat.enableEdgeToEdge
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        enableEdgeToEdge(requireActivity().window)

        bannerAdapter = BannerAdapter(childFragmentManager, lifecycle)
        binding.viewPager.adapter = bannerAdapter

        categoryAdapter = CategoryAdapter()
        binding.categoriesRecyclerView.adapter = categoryAdapter
        productsAdapter = ProductsAdapter()
        binding.productsRecyclerVew.adapter = productsAdapter

        categoryAdapter.setOnItemClickListener { category ->
            viewModel.selectedCategory(category.id)
            val menuList = productsAdapter.currentList
            val categoryPosition = menuList.indexOfFirst { it.id == category.id }
            if (categoryPosition != -1) {
                val scroller = object : androidx.recyclerview.widget.LinearSmoothScroller(requireContext()) {
                    override fun getVerticalSnapPreference(): Int = SNAP_TO_START
                }
                scroller.targetPosition = categoryPosition

                binding.productsRecyclerVew.layoutManager?.startSmoothScroll(scroller)
            }
         }

        binding.productsRecyclerVew.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisiblePos = layoutManager.findFirstVisibleItemPosition()

                if (firstVisiblePos != RecyclerView.NO_POSITION) {
                    val categoryId = productsAdapter.currentList[firstVisiblePos].id
                    viewModel.selectedCategory(categoryId)
                    binding.categoriesRecyclerView.smoothScrollToPosition(firstVisiblePos)
                }
            }
        })

        binding.btnNotification.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeScreen_to_notificationScreen
            )
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
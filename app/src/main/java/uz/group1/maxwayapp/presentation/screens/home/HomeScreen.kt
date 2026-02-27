package uz.group1.maxwayapp.presentation.screens.home

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.databinding.ScreenHomeBinding
import uz.group1.maxwayapp.presentation.screens.profile.address.AddressBottomSheet
import uz.group1.maxwayapp.domain.models.HomeItem
import uz.group1.maxwayapp.presentation.screens.base_fragment.BaseFragment
import uz.group1.maxwayapp.presentation.screens.base_fragment.SystemBarConfig
import uz.group1.maxwayapp.presentation.screens.base_fragment.SystemBarIconStyle
import uz.group1.maxwayapp.presentation.screens.home.adapter.CategoryAdapter
import uz.group1.maxwayapp.presentation.screens.home.adapter.HomeMainAdapter
import uz.group1.maxwayapp.presentation.screens.home.adapter.StoriesAdapter
import uz.group1.maxwayapp.presentation.screens.home.banner.BannerAdapter
import uz.group1.maxwayapp.presentation.screens.home.product_infobottomsheet.ProductInfoBottomSheet
import uz.group1.maxwayapp.utils.GlobalVariables

class HomeScreen: BaseFragment(R.layout.screen_home) {

    private val binding by viewBinding(ScreenHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels<HomeViewModelImpl> { HomeViewModelFactory() }

    private lateinit var bannerAdapter: BannerAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    private lateinit var productsAdapter: HomeMainAdapter
    private lateinit var storiesAdapter: StoriesAdapter
    private var autoScJob: Job? = null
    private var isFullyLoaded = false

    override val applyBottomInset: Boolean
        get() = false

    override val systemBarConfig = SystemBarConfig(
        statusBarIcons = SystemBarIconStyle.DARK_ICONS,
        navigationBarIcons = SystemBarIconStyle.DARK_ICONS,
        fullscreen = false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRefreshListener()
        setUpAdapters()
        setUpClick()
        observe()

    }

    private fun setUpRefreshListener() {

        binding.productsRecyclerVew.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val isAtTop = !recyclerView.canScrollVertically(-1)
                binding.swipeRefresh.isEnabled = isAtTop
            }
        })

        binding.swipeRefresh.setOnRefreshListener({
            loadImitation()
        })
    }

    private fun setUpClick() {
        binding.btnNotification.setOnClickListener {
            findNavController().navigate(R.id.action_homeScreen_to_notificationScreen)
        }
        binding.btnSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeScreen_to_searchScreen)
        }
        childFragmentManager.setFragmentResultListener(
            AddressBottomSheet.RESULT_KEY, viewLifecycleOwner
        ) { _, _ ->
            findNavController().navigate(R.id.action_homeScreen_to_addAddressScreen)
        }

        binding.btnDelivery.setOnClickListener {
            AddressBottomSheet().show(childFragmentManager, "address_bottom_sheet")
        }
    }

    private fun setUpAdapters() {
        bannerAdapter = BannerAdapter(childFragmentManager, lifecycle)
        binding.viewPager.adapter = bannerAdapter

        categoryAdapter = CategoryAdapter()

        productsAdapter = HomeMainAdapter(
            onCountChanged = { product, count ->
                viewModel.updateProductCount(product.id, count)
            },
            onItemClick = { product, position ->
                val bottomSheet = ProductInfoBottomSheet.newInstance(
                    id = product.id,
                    name = product.name,
                    desc = product.description,
                    price = product.cost,
                    imageRes = product.image
                )

                bottomSheet.show(parentFragmentManager, "ProductInfoTag")
            }
        )

        val manager = GridLayoutManager(requireContext(), 2)
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (productsAdapter.getItemViewType(position) == HomeMainAdapter.TYPE_HEADER) 2 else 1
            }
        }

        binding.categoriesRecyclerView.adapter = categoryAdapter

        binding.productsRecyclerVew.layoutManager = manager
        binding.productsRecyclerVew.adapter = productsAdapter

        categoryAdapter.setOnItemClickListener { category ->
            viewModel.selectedCategory(category.id)

            val menuList = productsAdapter.currentList
            val categoryPosition = menuList.indexOfFirst { item ->
                item is HomeItem.CategoryHeader && item.id == category.id
            }

            if (categoryPosition != -1) {
                if (categoryPosition == 0) {
                    binding.mainMotionLayout.transitionToStart()
                } else {
                    binding.mainMotionLayout.transitionToEnd()
                }

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
                    val currentItem = productsAdapter.currentList[firstVisiblePos]

                    val categoryId = when (currentItem) {
                        is HomeItem.CategoryHeader -> currentItem.id
                        is HomeItem.ProductItem -> currentItem.product.categoryID
                    }

                    viewModel.selectedCategory(categoryId)
                }
            }
        })
        storiesAdapter = StoriesAdapter()
        storiesAdapter.setOnItemClickListener { data, i ->
            findNavController().navigate(
                R.id.action_homeScreen_to_storiesScreen,
                bundleOf("currentPosition" to i, "currentData" to data)
            )
            GlobalVariables.stateVisibilityBottomNav.postValue(false)
        }
        binding.storiesRv.adapter = storiesAdapter
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.homeScreenElements.collect { state ->
                    handleLoadingState(state.isLoading, state.isError)

                        if (!state.isLoading) {
                            if (state.banners.isNotEmpty()) {
                                bannerAdapter.submitList(state.banners)
                                setupBannerAutoScroll(state.banners.size)
                            }

                            categoryAdapter.submitList(state.categories)

                            val homeItems = mutableListOf<HomeItem>()
                            state.menu.forEach { categoryMenu ->
                                homeItems.add(
                                    HomeItem.CategoryHeader(
                                        categoryMenu.id,
                                        categoryMenu.name
                                    )
                                )

                                categoryMenu.products.forEach { product ->
                                    homeItems.add(HomeItem.ProductItem(product))
                                }
                            }
                            productsAdapter.submitList(homeItems)

                            storiesAdapter.submitList(state.stories)

                            if (state.stories.isEmpty() || state.banners.isEmpty() || state.categories.isEmpty()) {
                                isFullyLoaded = false
                            } else {
                                isFullyLoaded = true
                            }
                        }
                }
            }
        }
    }

    private fun handleLoadingState(isLoading: Boolean, isError: Boolean) {
        val hasData = productsAdapter.itemCount > 0

        if (isLoading) {
            if (hasData) {
                binding.swipeRefresh.isRefreshing = true
                binding.shimmerLayout.visibility = View.GONE
            } else {
                binding.swipeRefresh.isRefreshing = false
                binding.shimmerLayout.visibility = View.VISIBLE
                binding.shimmerLayout.startShimmer()
                binding.mainMotionLayout.visibility = View.GONE
                binding.layoutEmpty.visibility = View.GONE
            }
        } else {
            binding.swipeRefresh.isRefreshing = false
            binding.shimmerLayout.stopShimmer()
            binding.shimmerLayout.visibility = View.GONE

            if (isError && !hasData) {
                binding.layoutEmpty.visibility = View.VISIBLE
                binding.mainMotionLayout.visibility = View.GONE
            } else {
                binding.layoutEmpty.visibility = View.GONE
                binding.mainMotionLayout.visibility = View.VISIBLE
            }
        }
    }
    private fun setupBannerAutoScroll(size: Int) {
        if (autoScJob == null) {
            val middlePosition = (Int.MAX_VALUE / 2) - ((Int.MAX_VALUE / 2) % size)
            binding.viewPager.setCurrentItem(middlePosition, false)
            startAutoScrooll()
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

    private fun loadImitation() {
        lifecycleScope.launch {
            binding.mainMotionLayout.isVisible = false
            binding.shimmerLayout.isVisible = true
            delay(1200)
            binding.mainMotionLayout.isVisible = true
            binding.shimmerLayout.isVisible = false
            binding.swipeRefresh.isRefreshing = false
        }
    }
}


package uz.group1.maxwayapp.presentation.screens.stories

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.gita.leeson_network.utils.NetworkConnectionCallback
import uz.gita.leeson_network.utils.NetworkMonitor
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.data.model.StoryUIData
import uz.group1.maxwayapp.databinding.ScreenStoriesBinding
import uz.group1.maxwayapp.presentation.adapters.StoryAdapter
import uz.group1.maxwayapp.utils.showNotification

class StoriesScreen: Fragment(R.layout.screen_stories) {

    private val binding by viewBinding(ScreenStoriesBinding::bind)
    private val viewModel by viewModels<StoriesViewModelImpl> { StoriesViewModelFactory() }
    private lateinit var networkMonitor: NetworkMonitor

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()
        setupTouchListener()

        networkMonitor = NetworkMonitor(requireContext().applicationContext)

        networkMonitor.startMonitoring(object : NetworkConnectionCallback {
            override fun onNetworkAvailable() {
                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                    if (isAdded) {
                        if (viewModel.storiesLiveData.value.isNullOrEmpty()){
                            viewModel.loadStories()
                        }else{
                            viewModel.setPauseState(false)
                        }
                    }
                }
            }

            override fun onNetworkLost() {
                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                    if (isAdded) {
                        showNotification("Internet mavjud emas!",false)
                        viewModel.setPauseState(true)
                    }
                }
            }

            override fun onNetworkLosing() {
            }

            override fun onNetworkUnavailable() {
                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                    if (isAdded) {
                        showNotification("Offline",false)

                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        networkMonitor.stopMonitoring()
    }

    private fun setUpObservers() {
        viewModel.storiesLiveData.observe(viewLifecycleOwner) { list ->
            if (list.isNotEmpty()) {
                setupViewPager(list)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.storyTimerFlow.collect { (index, progress) ->
                        updateUI(index, progress)
                    }
                }
                launch {
                    viewModel.navigateToPage.collect { nextIndex ->
                        binding.viewPager.setCurrentItem(nextIndex, true)
                    }
                }
            }
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {errorMessage->
            showNotification(errorMessage, isSuccess = false)
        }
        viewModel.progressLiveData.observe(viewLifecycleOwner){
            binding.loader.isVisible = it
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupTouchListener() {
        val internalRecyclerView = binding.viewPager.getChildAt(0)

        internalRecyclerView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    viewModel.setPauseState(true)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    viewModel.setPauseState(false)
                }
            }
            false
        }
    }

    private fun setupViewPager(list: List<StoryUIData>) {
        val storyAdapter = StoryAdapter(list)
        binding.viewPager.apply {
            adapter = storyAdapter

            setPageTransformer { page, position ->
                page.alpha = 1 - Math.abs(position)
            }
            binding.viewPager.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    viewModel.onPageSelected(position)
                }
            })
        }
    }

    private fun updateUI(index: Int, progress: Int) {
        if (binding.viewPager.currentItem != index) {
            binding.viewPager.setCurrentItem(index, true)
        }

        val bar = binding.storyProgressBar
        bar.progress = progress
    }

}
package uz.group1.maxwayapp.presentation.screens.stories

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.data.model.StoryUIData
import uz.group1.maxwayapp.databinding.ScreenStoriesBinding
import uz.group1.maxwayapp.presentation.adapters.StoryAdapter
import uz.group1.maxwayapp.utils.loadImage
import uz.group1.maxwayapp.utils.showNotification

class StoriesScreen: Fragment(R.layout.screen_stories) {

    private val binding by viewBinding(ScreenStoriesBinding::bind)
    private val viewModel by viewModels<StoriesViewModelImpl> { StoriesViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()

    }

    private fun setUpObservers() {
        viewModel.storiesLiveData.observe(viewLifecycleOwner) { list ->
            if (list.isNotEmpty()) {
                setupViewPager(list)
                setupProgressBars(list.size)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.storyTimerFlow.collect { (index, progress) ->
                    updateUI(index, progress)
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

    private fun setupViewPager(list: List<StoryUIData>) {
        val storyAdapter = StoryAdapter(list)
        binding.viewPager.apply {
            adapter = storyAdapter
            isUserInputEnabled = false

            setPageTransformer { page, position ->
                page.alpha = 1 - Math.abs(position)
            }
        }
    }

    private fun setupProgressBars(size: Int) {
        binding.progressContainer.removeAllViews()
        for (i in 0 until size) {
            val progressView = ProgressBar(
                context,
                null,
                android.R.attr.progressBarStyleHorizontal
            ).apply {
                layoutParams = LinearLayout.LayoutParams(0, 8, 1f).apply {
                    setMargins(4, 0, 4, 0)
                }
                max = 100
                progress = 0
                progressDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.story_progress_bg)
            }
            binding.progressContainer.addView(progressView)
        }
    }

    private fun updateUI(index: Int, progress: Int) {
        if (binding.viewPager.currentItem != index) {
            binding.viewPager.setCurrentItem(index, true)
        }

        for (i in 0 until binding.progressContainer.childCount) {
            val bar = binding.progressContainer.getChildAt(i) as ProgressBar
            when {
                i < index -> bar.progress = 100
                i == index -> bar.progress = progress
                else -> bar.progress = 0
            }
        }
    }

}
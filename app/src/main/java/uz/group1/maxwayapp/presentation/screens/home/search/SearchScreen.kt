package uz.group1.maxwayapp.presentation.screens.home.search

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.databinding.ScreenSearchBinding

class SearchScreen : Fragment(R.layout.screen_search) {
    private val binding by viewBinding(ScreenSearchBinding::bind)
    private val viewModel: SearchViewModel by viewModels { SearchViewModelFactory() }
    private val adapter by lazy { SearchAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupListeners()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            adapter = this@SearchScreen.adapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.etSearch.addTextChangedListener {
            val query = it.toString()
            viewModel.search(query)
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoading.collectLatest { isLoading ->
                binding.apply {
                    searchProgress.isVisible = isLoading

                    if (isLoading) {
                        shimmerLayout.isVisible = true
                        shimmerLayout.startShimmer()
                        recyclerView.isVisible = false
                        layoutEmpty.isVisible = false
                    } else {
                        shimmerLayout.stopShimmer()
                        shimmerLayout.isVisible = false
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.products.collectLatest { list ->
                val query = binding.etSearch.text.toString().trim()
                val isLoading = viewModel.isLoading.value

                adapter.submitList(list)

                binding.apply {
                    if (!isLoading) {
                        when {
                            list.isEmpty() && query.isNotEmpty() -> {
                                layoutEmpty.isVisible = true
                                recyclerView.isVisible = false
                            }
                            list.isNotEmpty() -> {
                                layoutEmpty.isVisible = false
                                recyclerView.isVisible = true
                            }
                            else -> {
                                layoutEmpty.isVisible = false
                                recyclerView.isVisible = false
                            }
                        }
                    }
                }
            }
        }
    }
}
package uz.group1.maxwayapp.presentation.screens.profile.filial.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.databinding.PageListBinding
import uz.group1.maxwayapp.presentation.screens.base_fragment.BaseFragment
import uz.group1.maxwayapp.presentation.screens.profile.filial.FilialViewModel
import uz.group1.maxwayapp.presentation.screens.profile.filial.FilialViewModelFactory
import uz.group1.maxwayapp.presentation.screens.profile.filial.FilialViewModelImpl

class ListScreen: Fragment(R.layout.page_list) {
    private val binding by viewBinding(PageListBinding::bind)
    private lateinit var adapter: FilialListAdapter
    private val viewModel: FilialViewModel by viewModels<FilialViewModelImpl> { FilialViewModelFactory() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FilialListAdapter()
        binding.recyclerView.adapter = adapter

        viewModel.loadBranchList()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.branchListFlow.collect { result ->
                result?.onSuccess { list ->
                    adapter.submitList(list)
                }
            }
        }

    }
}
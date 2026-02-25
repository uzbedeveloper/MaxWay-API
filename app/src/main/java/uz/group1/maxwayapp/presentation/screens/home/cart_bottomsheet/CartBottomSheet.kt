package uz.group1.maxwayapp.presentation.screens.home.cart_bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.data.mapper.toProductUIData
import uz.group1.maxwayapp.data.model.ProductUIData
import uz.group1.maxwayapp.data.repository_impl.ProductRepositoryImpl
import uz.group1.maxwayapp.databinding.DialogBottomsheetCartBinding
import uz.group1.maxwayapp.databinding.DialogConfirmBinding
import uz.group1.maxwayapp.domain.repository.ProductRepository
import uz.group1.maxwayapp.presentation.screens.home.adapter.CartAdapter
import uz.group1.maxwayapp.presentation.screens.home.adapter.CartRecommendAdapter
import uz.group1.maxwayapp.presentation.screens.home.dialogs.ConfirmDialog

class CartBottomSheet : BottomSheetDialogFragment(R.layout.dialog_bottomsheet_cart) {
    private val binding by viewBinding(DialogBottomsheetCartBinding::bind)

    private val repository: ProductRepository = ProductRepositoryImpl.getInstance()
    private var currentIds: List<Int>? = null

    private val cartAdapter by lazy {
        CartAdapter { product, newCount ->
            repository.updateProductCount(product.id, newCount)
        }
    }

    private val recommendedAdapter by lazy {
        CartRecommendAdapter { product, newCount ->
            repository.updateProductCount(product.id, newCount)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()
        setUpObserver()

        binding.btnChooseDish.setOnClickListener { dismiss() }

        binding.btnBuy.setOnClickListener {
            ConfirmDialog().show(childFragmentManager, "ConfirmDialog")
        }

    }

    private fun setupRecyclerViews() {
        binding.cartRecyclerView.adapter = cartAdapter
        binding.recommendedRecyclerView.adapter = recommendedAdapter
    }

    private fun setUpObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repository.getMenu()
                .collect { categories ->
                    binding.shimmerView.visibility = View.VISIBLE
                    binding.shimmerView.startShimmer()
                    binding.recommendedRecyclerView.visibility = View.GONE

                    val addedProducts = categories.flatMap { it.products }.filter { it.count > 0 }
                    if (addedProducts.isEmpty()) {
                        showEmptyState()
                    } else {
                        showCartItems(addedProducts)
                        updateRecommendations(addedProducts.map { it.id })
                    }
            }
        }
    }

    private fun showCartItems(items: List<ProductUIData>) {
        binding.layoutEmpty.visibility = View.GONE
        binding.layoutCartContent.visibility = View.VISIBLE

        cartAdapter.submitList(items)

        val total = items.sumOf { it.cost * it.count }
        binding.textTotalPrice.text = String.format("%,d сум", total)
    }

    private fun showEmptyState() {
        binding.layoutEmpty.visibility = View.VISIBLE
        binding.layoutCartContent.visibility = View.GONE
    }

    private suspend fun updateRecommendations(ids: List<Int>) {

        if (ids == currentIds) {
            binding.shimmerView.stopShimmer()
            binding.shimmerView.visibility = View.GONE
            binding.recommendedRecyclerView.visibility = View.VISIBLE
            return
        }
        currentIds = ids

        repository.getRecommendedProducts(ids).onSuccess { response ->
            val list = response.data.map {
                it.toProductUIData()
            }
            binding.layoutRecommended.visibility = if (list.isNotEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
            recommendedAdapter.submitList(list) {
                viewLifecycleOwner.lifecycleScope.launch {
                    delay(100)
                    binding.layoutRecommended.visibility = if (list.isNotEmpty()) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }

                    binding.shimmerView.stopShimmer()
                    binding.shimmerView.visibility = View.GONE
                    binding.recommendedRecyclerView.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog as? BottomSheetDialog
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

        bottomSheet?.let {
            val behavior = BottomSheetBehavior.from(it)

            val layoutParams = it.layoutParams
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
            it.layoutParams = layoutParams

            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.skipCollapsed = true
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        return dialog
    }

}
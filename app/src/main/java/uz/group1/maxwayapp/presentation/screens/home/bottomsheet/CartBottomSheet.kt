package uz.group1.maxwayapp.presentation.screens.home.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.data.model.ProductUIData
import uz.group1.maxwayapp.data.repository_impl.ProductRepositoryImpl
import uz.group1.maxwayapp.databinding.DialogBottomsheetCartBinding
import uz.group1.maxwayapp.domain.repository.ProductRepository
import uz.group1.maxwayapp.presentation.screens.home.adapter.CartAdapter

class CartBottomSheet : BottomSheetDialogFragment() {

    private val binding by viewBinding(DialogBottomsheetCartBinding::bind)

    private val repository: ProductRepository = ProductRepositoryImpl.getInstance()

    private val cartAdapter by lazy {
        CartAdapter { product, newCount ->
            repository.updateProductCount(product.id, newCount)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.dialog_bottomsheet_cart, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                repository.getMenu().collect { categories ->
                    val addedProducts = categories.flatMap { it.products }.filter { it.count > 0 }

                    Log.d("TTT", "BottomSheet from Repo: ${addedProducts.size} items")

                    if (addedProducts.isEmpty()) {
                        showEmptyState()
                    } else {
                        showCartItems(addedProducts)
                    }
                }
            }
        }

        binding.btnChooseDish.setOnClickListener { dismiss() }
        binding.btnBuy.setOnClickListener { dismiss() }
    }

    private fun setupRecyclerView() {
        binding.cartRecyclerView.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun showEmptyState() {
        binding.layoutEmpty.visibility = View.VISIBLE
        binding.layoutCartContent.visibility = View.GONE
    }

    private fun showCartItems(items: List<ProductUIData>) {
        binding.layoutEmpty.visibility = View.GONE
        binding.layoutCartContent.visibility = View.VISIBLE

        cartAdapter.submitList(items)

        val total = items.sumOf { it.cost * it.count }
        binding.textTotalPrice.text = String.format("%,d сум", total)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        dialog.behavior.skipCollapsed = true
        dialog.window?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            ?.setBackgroundResource(android.R.color.transparent)
        return dialog
    }
    override fun onStart() {
        super.onStart()
        val dialog = dialog as? BottomSheetDialog
        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

        bottomSheet?.let {
            it.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            val behavior = BottomSheetBehavior.from(it)

            behavior.isFitToContents = false
            behavior.halfExpandedRatio = 0.7f
            behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        }
    }

}
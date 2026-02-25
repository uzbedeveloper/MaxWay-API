package uz.group1.maxwayapp.presentation.screens.home.product_infobottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.data.repository_impl.ProductRepositoryImpl
import uz.group1.maxwayapp.databinding.DialogBottomsheetProductInfoBinding
import uz.group1.maxwayapp.domain.repository.ProductRepository
import uz.group1.maxwayapp.utils.loadImage

class ProductInfoBottomSheet : BottomSheetDialogFragment() {

    private val binding by viewBinding(DialogBottomsheetProductInfoBinding::bind)

    private val repository: ProductRepository = ProductRepositoryImpl.getInstance()

    private var currentCount = 0
    private var productId: Int = -1
    private var price = 0

    companion object {
        private const val ARG_PRODUCT_ID = "arg_product_id"
        private const val ARG_PRODUCT_NAME = "arg_product_name"
        private const val ARG_PRODUCT_DESC = "arg_product_desc"
        private const val ARG_PRODUCT_PRICE = "arg_product_price"
        private const val ARG_PRODUCT_IMAGE = "arg_product_image"

        fun newInstance(
            id: Int,
            name: String,
            desc: String,
            price: Int,
            imageRes: String
        ): ProductInfoBottomSheet {
            val fragment = ProductInfoBottomSheet()
            val args = Bundle().apply {
                putInt(ARG_PRODUCT_ID, id)
                putString(ARG_PRODUCT_NAME, name)
                putString(ARG_PRODUCT_DESC, desc)
                putInt(ARG_PRODUCT_PRICE, price)
                putString(ARG_PRODUCT_IMAGE, imageRes)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.dialog_bottomsheet_product_info, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            productId = it.getInt(ARG_PRODUCT_ID)
            binding.layoutInfoContent.visibility = View.VISIBLE
            binding.tvProduct.text = it.getString(ARG_PRODUCT_NAME)
            binding.tvDesc.text = it.getString(ARG_PRODUCT_DESC)
            price = it.getInt(ARG_PRODUCT_PRICE)
            binding.tvPrice.text = "${price} сум"
            binding.imgProduct.loadImage(it.getString(ARG_PRODUCT_IMAGE) ?: "") {
            }
            fetchInitialCount()
        }

        setupCounter()

        binding.btnAddToCart.setOnClickListener {
            if (productId != -1) {
                repository.updateProductCount(productId, currentCount)
                dismiss()
            }
        }

        binding.btnRetry.setOnClickListener { dismiss() }
    }

    private fun setupCounter() {
        binding.btnPlus.setOnClickListener {
            currentCount++
            updateCounterUI()
        }

        binding.btnMinus.setOnClickListener {
            if (currentCount > 1) {
                currentCount--
                updateCounterUI()
            }
        }
    }

    private fun fetchInitialCount() {
        viewLifecycleOwner.lifecycleScope.launch {
            repository.getMenu().collect { categories ->
                val product = categories.flatMap { it.products }.find { it.id == productId }

                product?.let {
                    currentCount = if (it.count > 0) it.count else 1
                    updateCounterUI()
                }
            }
        }
    }

    private fun updateCounterUI() {
        binding.textCount.text = currentCount.toString()

        binding.tvPrice.text = (price * currentCount).toString() + "сум"

        if (productId != -1) {
            repository.updateProductCount(productId, currentCount)
        }

    }

    private fun showEmptyState() {
        binding.layoutEmpty.visibility = View.VISIBLE
        binding.layoutInfoContent.visibility = View.GONE
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
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

        bottomSheet?.let {
            val displayMetrics = requireContext().resources.displayMetrics
            val height70Percent = (displayMetrics.heightPixels * 0.7).toInt()

            it.layoutParams.height = height70Percent

            val behavior = BottomSheetBehavior.from(it)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.peekHeight = height70Percent
            behavior.isFitToContents = true
        }
    }

}
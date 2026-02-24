package uz.group1.maxwayapp.presentation.screens.home.dialogs

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.data.repository_impl.ProductRepositoryImpl
import uz.group1.maxwayapp.data.sources.remote.request.orders.createOrder.CreateOrderRequest
import uz.group1.maxwayapp.data.sources.remote.request.orders.createOrder.OrderItem
import uz.group1.maxwayapp.databinding.DialogConfirmBinding
import uz.group1.maxwayapp.presentation.screens.home.adapter.CartAdapter
import uz.group1.maxwayapp.utils.NotificationType
import uz.group1.maxwayapp.utils.showNotification

class ConfirmDialog : DialogFragment(R.layout.dialog_confirm) {

    private var _binding: DialogConfirmBinding? = null
    private val binding get() = _binding!!

    private val repository = ProductRepositoryImpl.getInstance()

    private val adapter by lazy {
        CartAdapter({ product, newCount ->
            repository.updateProductCount(product.id, newCount)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = DialogConfirmBinding.bind(view)

        setupUI()
        loadData()
    }

    private fun setupUI() {
        binding.cartRecyclerView.adapter = adapter

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnConfirm.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    val categories = repository.getMenu().first()

                    val addedProducts = categories.flatMap { it.products }.filter { it.count > 0 }

                    if (addedProducts.isEmpty()) return@launch

                    val request = CreateOrderRequest(
                        ls = addedProducts.map { OrderItem(it.id, it.count) }
                    )

                    val result = repository.confirmOrder(request)

                    if (result.isSuccess) {
                        requireActivity().showNotification("Buyurtmangiz qabul qilindi", NotificationType.SUCCESS)
                        repository.clearCart()
                        (parentFragment as? BottomSheetDialogFragment)?.dismiss()
                        dismiss()
                    } else {
                        requireActivity().showNotification(result.exceptionOrNull()?.message.toString(), NotificationType.WARNING)
                        Log.e("API_ERROR", result.exceptionOrNull()?.message.toString())
                    }
                } catch (e: Exception) {
                    Log.e("CONFIRM_ERROR", e.message.toString())
                }
            }
        }

        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        isCancelable = false
    }

    private fun loadData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repository.getMenu().collect { categories ->
                val items = categories.flatMap { it.products }.filter { it.count > 0 }
                adapter.submitList(items)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.95).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
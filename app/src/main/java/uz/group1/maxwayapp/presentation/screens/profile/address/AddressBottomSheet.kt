package uz.group1.maxwayapp.presentation.screens.profile.address

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.data.repository_impl.AddressRepositoryImpl
import uz.group1.maxwayapp.databinding.DialogBottomsheetAddressBinding
import uz.group1.maxwayapp.domain.repository.AddressRepository
import uz.group1.maxwayapp.utils.NotificationType
import uz.group1.maxwayapp.utils.dpToPx
import uz.group1.maxwayapp.utils.showNotification

class AddressBottomSheet : BottomSheetDialogFragment() {

    companion object {
        const val RESULT_KEY = "add_address_clicked"
    }

    private val binding by viewBinding(DialogBottomsheetAddressBinding::bind)
    private val repository: AddressRepository = AddressRepositoryImpl.getInstance()

    private val adapter by lazy {
        AddressAdapter { address ->
            deleteAddress(address.id)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.dialog_bottomsheet_address, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvAddresses.adapter = adapter

        binding.btnAddAddress.setOnClickListener {
            dismiss()
            parentFragmentManager.setFragmentResult(RESULT_KEY, Bundle())
        }

        loadAddresses()
    }

    private fun loadAddresses() {
        viewLifecycleOwner.lifecycleScope.launch {
            val result = repository.getAddresses()
            result.onSuccess { list ->
                if (list.isEmpty()) {
                    binding.layoutEmpty.visibility = View.VISIBLE
                    binding.rvAddresses.visibility = View.GONE
                } else {
                    binding.layoutEmpty.visibility = View.GONE
                    binding.rvAddresses.visibility = View.VISIBLE
                    adapter.submitList(list)
                }
            }.onFailure { error ->
                dismiss()
                Snackbar.make(binding.root, error.message ?: "Xatolik", 2000).show()
                if (error is CancellationException) return@onFailure
                requireActivity().showNotification(error.message ?: "Xatolik",
                    NotificationType.ERROR)
            }
        }
    }

    private fun deleteAddress(id: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            val result = repository.deleteAddress(id)
            result.onSuccess {
                loadAddresses()
            }.onFailure { error ->
                Snackbar.make(binding.root, error.message ?: "O'chirishda xatolik", 2000).show()
                if (error is CancellationException) return@onFailure
                requireActivity().showNotification(error.message ?: "O'chirishda xatolik",
                    NotificationType.ERROR)
            }
        }
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
            behavior.halfExpandedRatio = 0.8f
            behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        }
    }
}
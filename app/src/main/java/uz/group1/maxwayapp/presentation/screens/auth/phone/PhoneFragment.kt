package uz.group1.maxwayapp.presentation.screens.auth.phone

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import uz.group1.maxwayapp.databinding.FragmentRegisterPhoneBinding

class PhoneFragment : Fragment() {

    companion object {
        private const val TAG = "PhoneFragment"
        private const val PREFIX = "+998 "
    }

    private var _binding: FragmentRegisterPhoneBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PhoneViewModel by viewModels {
        PhoneViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterPhoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated()")
        
        if (binding.etPhone.text.isNullOrEmpty()) {
            binding.etPhone.setText(PREFIX)
            binding.etPhone.setSelection(PREFIX.length)
        }

        setupListeners()
        observeUiState()
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            if (!findNavController().popBackStack()) {
                requireActivity().finish()
            }
        }

        binding.etPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val currentText = s.toString()
                
                if (!currentText.startsWith(PREFIX)) {
                    binding.etPhone.setText(PREFIX)
                    binding.etPhone.setSelection(PREFIX.length)
                    return
                }

                val rawDigits = currentText.substring(PREFIX.length).replace(" ", "")
                if (rawDigits.length > 9) {
                    val fixedText = PREFIX + rawDigits.substring(0, 9)
                    binding.etPhone.setText(fixedText)
                    binding.etPhone.setSelection(fixedText.length)
                    return
                }

                val isValid = rawDigits.length == 9
                binding.btnContinue.isEnabled = isValid
                binding.btnContinue.alpha = if (isValid) 1f else 0.5f
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btnContinue.setOnClickListener {
            val phone = binding.etPhone.text.toString().replace(" ", "")
            viewModel.sendPhone(phone)
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is PhoneUiState.Idle -> Unit
                    is PhoneUiState.Loading -> {
                        binding.btnContinue.isEnabled = false
                        binding.btnContinue.text = "Yuklanmoqda..."
                    }
                    is PhoneUiState.Success -> {
                        val phone = binding.etPhone.text.toString().replace(" ", "")
                        binding.btnContinue.isEnabled = true
                        binding.btnContinue.text = "Продолжить"
                        findNavController().navigate(
                            PhoneFragmentDirections.actionPhoneFragmentToSmsCodeFragment(phone)
                        )

                        viewModel.resetState()
                    }
                    is PhoneUiState.Error -> {
                        binding.btnContinue.isEnabled = true
                        binding.btnContinue.text = "Продолжить"
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

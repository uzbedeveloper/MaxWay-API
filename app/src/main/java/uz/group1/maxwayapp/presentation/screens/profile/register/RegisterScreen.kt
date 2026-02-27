package uz.group1.maxwayapp.presentation.screens.profile.register

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.databinding.ScreenRegisterPhoneBinding
import uz.group1.maxwayapp.presentation.screens.base_fragment.BaseFragment

class RegisterScreen: BaseFragment(R.layout.screen_register_phone) {
    private val binding by viewBinding(ScreenRegisterPhoneBinding::bind)
    private val viewModel: RegisterViewModel by viewModels { RegisterViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnContinueLogin.setOnClickListener {
            val phone = "+"+binding.etPhone.text.toString().replace(Regex("\\D"), "")

            viewModel.register(phone)
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack(R.id.homeScreen, false)
        }

        observeViewModel()
        setupPhoneFormatter()
    }

    private fun updateButtonState(isReady: Boolean) {
        binding.btnContinueLogin.isEnabled = isReady
        binding.btnContinueLogin.backgroundTintList = ColorStateList.valueOf(if (isReady) Color.parseColor("#8150A0") else Color.parseColor("#F2F2F2"))
        binding.btnContinueLogin.setTextColor(
            if (isReady) Color.WHITE else Color.parseColor("#FFFFFF")
        )
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.registerSuccess.collect { phone ->
                val bundle = Bundle().apply { putString("phone", phone) }
                findNavController().navigate(R.id.verifyScreen, bundle)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.errorFlow.collect {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun setupPhoneFormatter() {
        binding.etPhone.addTextChangedListener(object : TextWatcher {
            private var isUpdating = false
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (isUpdating || s == null) return
                isUpdating = true
                val digits = s.toString().replace(Regex("\\D"), "")
                val formatted = StringBuilder()
                for (i in digits.indices) {
                    when (i) {
                        0 -> formatted.append("+")
                        3, 5, 8, 10 -> formatted.append(" ")
                    }
                    formatted.append(digits[i])
                    if (i >= 11) break
                }
                val selection = binding.etPhone.selectionStart + (formatted.length - s.length)
                s.replace(0, s.length, formatted.toString())
                binding.etPhone.setSelection(selection.coerceIn(0, formatted.length))
                isUpdating = false


                val isReady = (if(digits.length==12) 13 else digits.length) == 13

                updateButtonState(isReady)
            }
        })
    }
}
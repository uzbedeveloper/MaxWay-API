package uz.group1.maxwayapp.presentation.screens.profile.register

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
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

class RegisterScreen: Fragment(R.layout.screen_register_phone) {
    private val binding by viewBinding(ScreenRegisterPhoneBinding::bind)
    private val viewModel: RegisterViewModel by viewModels { RegisterViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etPhone.addTextChangedListener {
            val text = it.toString()
            if (!text.startsWith("+998")) {
                binding.etPhone.setText("+998")
                binding.etPhone.setSelection(4)
            }

            val isReady = text.length == 13
            updateButtonState(isReady)
        }

        binding.btnContinueLogin.setOnClickListener {
            viewModel.register(binding.etPhone.text.toString())
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        observeViewModel()
    }

    private fun updateButtonState(isReady: Boolean) {
        binding.btnContinueLogin.isEnabled = isReady
        binding.btnContinueLogin.backgroundTintList = ColorStateList.valueOf(if (isReady) Color.parseColor("#8150A0") else Color.parseColor("#F2F2F2"))
        binding.btnContinueLogin.setTextColor(
            if (isReady) Color.BLACK else Color.parseColor("#FFFFFF")
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
}
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
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.databinding.ScreenRegisterSmsCodeBinding
import uz.group1.maxwayapp.presentation.screens.base_fragment.BaseFragment
import uz.group1.maxwayapp.utils.NotificationType
import uz.group1.maxwayapp.utils.showNotification

class VerifyScreen : BaseFragment(R.layout.screen_register_sms_code) {

    private val binding by viewBinding(ScreenRegisterSmsCodeBinding::bind)
    private val viewModel: RegisterViewModel by viewModels { RegisterViewModelFactory() }
    private var phone: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        phone = arguments?.getString("phone") ?: ""
        binding.tvSmsNote.text = "Код отправили на номер $phone"

        viewModel.startTimer()
        setupOtpInputs()

        binding.btnBackVerify.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnResendSms.setOnClickListener {
            if (viewModel.timerFlow.value == 0) {
                clearOtpInputs()
                viewModel.resendCode(phone)
            }
        }

        binding.btnVerifyCode.setOnClickListener {
            val code = getEnteredCode()
            if (code.length == 4) {
                viewModel.verify(phone, code.toInt())
            }
        }

        observeViewModel()
    }

    private fun setupOtpInputs() {
        val editTexts = arrayOf(binding.etCode1, binding.etCode2, binding.etCode3, binding.etCode4)

        for (i in editTexts.indices) {
            editTexts[i].addTextChangedListener {
                val text = it.toString()
                if (text.length == 1 && i < editTexts.size - 1) {
                    editTexts[i + 1].requestFocus()
                }

                val code = getEnteredCode()
                checkButtonState(code)
            }

            editTexts[i].setOnKeyListener { _, keyCode, event ->
                if (keyCode == android.view.KeyEvent.KEYCODE_DEL && event.action == android.view.KeyEvent.ACTION_DOWN) {
                    if (editTexts[i].text.isEmpty() && i > 0) {
                        editTexts[i - 1].requestFocus()
                        editTexts[i - 1].setText("")
                    }
                }
                false
            }
        }
    }

    private fun clearOtpInputs() {
        val editTexts = arrayOf(binding.etCode1, binding.etCode2, binding.etCode3, binding.etCode4)
        editTexts.forEach { it.setText("") }
        editTexts[0].requestFocus()
    }

    private fun checkButtonState(code: String) {
        val isFull = code.length == 4
        binding.btnVerifyCode.isEnabled = isFull
        binding.btnVerifyCode.backgroundTintList = ColorStateList.valueOf(
            if (isFull) Color.parseColor("#8150A0") else Color.parseColor("#F2F3F5")
        )
        binding.btnVerifyCode.setTextColor(
            if (isFull) Color.WHITE else Color.parseColor("#A1A7B0")
        )
    }

    private fun getEnteredCode() = with(binding) {
        etCode1.text.toString() + etCode2.text.toString() +
                etCode3.text.toString() + etCode4.text.toString()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.loadingFlow.collect { isLoading ->
                binding.progressVerify.visibility = if (isLoading) View.VISIBLE else View.GONE
                binding.btnVerifyCode.text = if (isLoading) "" else "Продолжить"
                if (isLoading) {
                    binding.btnVerifyCode.isEnabled = false
                } else {
                    checkButtonState(getEnteredCode())
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.timerFlow.collect { sec ->
                if (sec > 0) {
                    binding.btnResendSms.text = "Отправить другой код 00:${sec.toString().padStart(2, '0')}"
                    binding.btnResendSms.isEnabled = false
                    binding.btnResendSms.alpha = 0.5f
                } else {
                    binding.btnResendSms.text = "Отправить код еще раз"
                    binding.btnResendSms.isEnabled = true
                    binding.btnResendSms.alpha = 1.0f
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.verifySuccess.collect { hasProfile ->
                if (hasProfile) {
                    findNavController().navigate(R.id.action_verifyScreen_to_profileScreen)
                } else {
                    findNavController().navigate(R.id.action_verifyScreen_to_createUserScreen)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.errorFlow.collect { msg ->
                requireActivity().showNotification(msg, NotificationType.ERROR)
            }
        }
    }
}
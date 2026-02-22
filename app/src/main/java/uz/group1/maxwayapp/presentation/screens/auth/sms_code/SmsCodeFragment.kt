package uz.group1.maxwayapp.presentation.screens.auth.sms_code

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch
import uz.group1.maxwayapp.databinding.FragmentRegisterSmsCodeBinding

class SmsCodeFragment : Fragment() {

    companion object {
        private const val TAG = "SmsCodeFragment"
    }

    private var _binding: FragmentRegisterSmsCodeBinding? = null
    private val binding get() = _binding!!
    private val args: SmsCodeFragmentArgs by navArgs()

    private val viewModel: SmsCodeViewModel by viewModels {
        SmsCodeViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterSmsCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "onViewCreated() → phone: ${args.phone}")
        
        // Kiritilgan telefon raqamini ekranda ko'rsatish
        binding.tvSmsNote.text = "Мы отправили SMS с кодом на номер\n${args.phone}"

        setupCodeBoxes()
        setupListeners()
        observeUiState()
    }

    private fun setupCodeBoxes() {
        val boxes = listOf(
            binding.etCode1, binding.etCode2, binding.etCode3,
            binding.etCode4, binding.etCode5, binding.etCode6
        )

        boxes.forEachIndexed { index, editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.length == 1 && index < boxes.lastIndex) {
                        boxes[index + 1].requestFocus()
                    }
                    val fullCode = boxes.joinToString("") { it.text.toString() }
                    val isComplete = fullCode.length == 6
                    binding.btnContinue.isEnabled = isComplete
                    binding.btnContinue.alpha = if (isComplete) 1f else 0.5f
                }
                override fun afterTextChanged(s: Editable?) {}
            })

            editText.setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_DEL
                    && event.action == KeyEvent.ACTION_DOWN
                    && editText.text.isNullOrEmpty()
                    && index > 0
                ) {
                    boxes[index - 1].requestFocus()
                    boxes[index - 1].text?.clear()
                    true
                } else false
            }
        }
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnContinue.setOnClickListener {
            val codeString = listOf(
                binding.etCode1, binding.etCode2, binding.etCode3,
                binding.etCode4, binding.etCode5, binding.etCode6
            ).joinToString("") { it.text.toString() }
            
            val code = codeString.toIntOrNull()

            if (code != null) {
                viewModel.verifyCode(phone = args.phone, code = code)
            } else {
                Toast.makeText(requireContext(), "Kod noto'g'ri", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvCountdownTimer.setOnClickListener {
            if (viewModel.canResend.value) {
                viewModel.resendCode(args.phone)
            }
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.timerText.collect { time ->
                        val canResend = viewModel.canResend.value
                        binding.tvCountdownTimer.text = if (canResend) "Qayta kod yuborish"
                        else "Время прибытия: $time"
                        binding.tvCountdownTimer.alpha = if (canResend) 1f else 0.6f
                    }
                }

                launch {
                    viewModel.uiState.collect { state ->
                        when (state) {
                            is SmsCodeUiState.Idle -> {
                                binding.btnContinue.isEnabled = true
                                binding.btnContinue.text = "Продолжить"
                            }
                            is SmsCodeUiState.Loading -> {
                                binding.btnContinue.isEnabled = false
                                binding.btnContinue.text = "Tekshirilmoqda..."
                            }
                            is SmsCodeUiState.Success -> {
                                findNavController().navigate(
                                    SmsCodeFragmentDirections.actionSmsCodeFragmentToNameFragment(args.phone)
                                )
                                viewModel.resetState()
                            }
                            is SmsCodeUiState.Error -> {
                                binding.btnContinue.isEnabled = true
                                binding.btnContinue.text = "Продолжить"
                                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                            }
                        }
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

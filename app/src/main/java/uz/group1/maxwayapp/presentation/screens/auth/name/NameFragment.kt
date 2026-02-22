package uz.group1.maxwayapp.presentation.screens.auth.name

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import uz.group1.maxwayapp.databinding.FragmentRegisterNameBinding

class NameFragment : Fragment() {

    private var _binding: FragmentRegisterNameBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NameViewModel by viewModels {
        NameViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        observeUiState()
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val isValid = s.toString().trim().length >= 2
                binding.btnContinue.isEnabled = isValid
                binding.btnContinue.alpha = if (isValid) 1f else 0.5f
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btnContinue.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            Log.d("NameFragment", "Davom etish bosildi: $name")
            viewModel.saveName(name)
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    Log.d("NameFragment", "Yangi holat: $state")
                    when (state) {
                        is NameUiState.Idle -> {
                            // Reset UI if needed
                        }
                        is NameUiState.Success -> {
                            Log.d("NameFragment", "Navigatsiya boshlanmoqda...")
                            try {
                                findNavController().navigate(
                                    NameFragmentDirections.actionNameFragmentToHomeActivity()
                                )
                                // State-ni reset qilamiz, aks holda fragmentga qaytsa yana navigatsiya bo'lib ketadi
                                viewModel.resetState()
                                
                                // Odatda Auth-dan keyin login ekranlarini stack-dan tozalash kerak
                                // Buning uchun nav_graph-da popUpTo ishlatish ma'qul, 
                                // yoki shu yerda activity-ni almashtirish.
                            } catch (e: Exception) {
                                Log.e("NameFragment", "Navigatsiya xatosi: ${e.message}")
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

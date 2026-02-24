package uz.group1.maxwayapp.presentation.screens.profile.register

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.data.repository_impl.AuthRepositoryImpl
import uz.group1.maxwayapp.databinding.ScreenRegisterNameBinding
import java.util.Calendar

class CreateUserScreen : Fragment(R.layout.screen_register_name) {
    private val binding by viewBinding(ScreenRegisterNameBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.etBirthday.setOnClickListener {
            showDatePicker()
        }

        binding.btnContinue.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val birthday = binding.etBirthday.text.toString().trim()

            if (name.isEmpty()) {
                binding.tilName.error = "Ismni kiriting"
                return@setOnClickListener
            }
            if (birthday.isEmpty()) {
                binding.tilBirthday.error = "Tug'ilgan kunni kiriting"
                return@setOnClickListener
            }
            binding.tilName.error = null
            binding.tilBirthday.error = null

            saveAndContinue(name, birthday)
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                val formatted = "%04d-%02d-%02d".format(year, month + 1, day)
                binding.etBirthday.setText(formatted)
            },
            calendar.get(Calendar.YEAR) - 20,
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun saveAndContinue(name: String, birthday: String) {
        binding.btnContinue.isEnabled = false
        viewLifecycleOwner.lifecycleScope.launch {
            val result = AuthRepositoryImpl.getInstance().updateUserInfo(name, birthday)
            result.onSuccess {
                findNavController().navigate(R.id.action_createUserScreen_to_profileScreen)
            }.onFailure { error ->
                binding.btnContinue.isEnabled = true
                Snackbar.make(binding.root, error.message ?: "Xatolik", 2000).show()
            }
        }
    }
}
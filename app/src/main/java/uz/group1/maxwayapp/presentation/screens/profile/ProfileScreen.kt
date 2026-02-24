package uz.group1.maxwayapp.presentation.screens.profile

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
import uz.group1.maxwayapp.data.sources.local.TokenManager
import uz.group1.maxwayapp.databinding.ScreenProfileBinding

class ProfileScreen: Fragment(R.layout.screen_profile) {
    private val binding by viewBinding(ScreenProfileBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnFilial.setOnClickListener {
            findNavController().navigate(
                R.id.action_profileScreen_to_filialScreen
            )
        }
        val token = TokenManager.getToken()
        val isAuthorized = !token.isNullOrEmpty()

        if (isAuthorized) {
            binding.cardUserInfo.visibility = View.VISIBLE
            binding.cardRegister.visibility = View.GONE
            binding.logout.visibility = View.VISIBLE

            binding.textUsername.text = TokenManager.getName()
            binding.textPhone.text = TokenManager.getPhone()
        } else {
            binding.cardUserInfo.visibility = View.GONE
            binding.cardRegister.visibility = View.VISIBLE
            binding.logout.visibility = View.GONE
        }

        binding.btnEnter.setOnClickListener {
            findNavController().navigate(R.id.action_profileScreen_to_registerScreen)
        }

        binding.btnEdit.setOnClickListener {
//            EditProfileBottomSheet { name, phone ->
//                binding.textUsername.text = name
//                binding.textPhone.text = phone
//            }.show(childFragmentManager, "edit_profile")
        }

        binding.logout.setOnClickListener {
//            LogoutBottomSheet {
//                viewLifecycleOwner.lifecycleScope.launch {
//                    val result = AuthRepositoryImpl.getInstance().deleteAccount()
//                    result.onSuccess {
//                        binding.cardUserInfo.visibility = View.GONE
//                        binding.cardRegister.visibility = View.VISIBLE
//                        binding.logout.visibility = View.GONE
//                    }.onFailure { error ->
//                        Snackbar.make(binding.root, error.message ?: "Xatolik", 2000).show()
//                    }
//                }
//            }.show(childFragmentManager, "logout")
        }

    }

}
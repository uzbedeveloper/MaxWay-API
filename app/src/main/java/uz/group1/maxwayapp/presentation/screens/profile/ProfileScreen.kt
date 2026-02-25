package uz.group1.maxwayapp.presentation.screens.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.data.repository_impl.ProductRepositoryImpl
import uz.group1.maxwayapp.data.sources.local.TokenManager
import uz.group1.maxwayapp.databinding.ScreenProfileBinding
import uz.group1.maxwayapp.presentation.screens.profile.address.AddressBottomSheet

class ProfileScreen: Fragment(R.layout.screen_profile) {
    private val binding by viewBinding(ScreenProfileBinding::bind)
    private val repository = ProductRepositoryImpl.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnFilial.setOnClickListener {
            findNavController().navigate(
                R.id.action_profileScreen_to_filialScreen
            )
        }

        childFragmentManager.setFragmentResultListener(
            AddressBottomSheet.RESULT_KEY, viewLifecycleOwner
        ) { _, _ ->
            findNavController().navigate(R.id.action_profileScreen_to_addAddressScreen)
        }

        binding.btnMyAddresses.setOnClickListener {
            AddressBottomSheet().show(childFragmentManager, "address_bottom_sheet")
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
            findNavController().navigate(R.id.action_profileScreen_to_createUserScreen)
        }

        binding.logout.setOnClickListener {
            repository.clear()
            binding.cardUserInfo.visibility = View.GONE
            binding.cardRegister.visibility = View.VISIBLE
            binding.logout.visibility = View.GONE
        }

    }

}
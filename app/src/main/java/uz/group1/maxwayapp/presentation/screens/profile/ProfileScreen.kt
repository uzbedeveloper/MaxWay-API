package uz.group1.maxwayapp.presentation.screens.profile

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.data.repository_impl.AuthRepositoryImpl
import uz.group1.maxwayapp.data.repository_impl.ProductRepositoryImpl
import uz.group1.maxwayapp.data.sources.local.TokenManager
import uz.group1.maxwayapp.databinding.ScreenProfileBinding
import uz.group1.maxwayapp.presentation.screens.base_fragment.BaseFragment
import uz.group1.maxwayapp.presentation.screens.base_fragment.SystemBarConfig
import uz.group1.maxwayapp.presentation.screens.base_fragment.SystemBarIconStyle
import uz.group1.maxwayapp.presentation.screens.profile.address.AddressBottomSheet
import uz.group1.maxwayapp.utils.NotificationType
import uz.group1.maxwayapp.utils.showNotification

@Suppress("DEPRECATION")
class ProfileScreen: BaseFragment(R.layout.screen_profile) {
    private val binding by viewBinding(ScreenProfileBinding::bind)
    private val repository = ProductRepositoryImpl.getInstance()
    private val authRepository = AuthRepositoryImpl.getInstance()
    override val applyBottomInset: Boolean = false
    override val applyTopInset: Boolean = true

    override val systemBarConfig = SystemBarConfig(
        statusBarIcons = SystemBarIconStyle.DARK_ICONS,
        navigationBarIcons = SystemBarIconStyle.DARK_ICONS,
        fullscreen = false
    )

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
            AlertDialog.Builder(requireContext())
                .setTitle("Выход")
                .setMessage("Вы уверены, что хотите выйти?")
                .setPositiveButton("Выйти") { _, _ ->
                    repository.clear()
                    binding.cardUserInfo.visibility = View.GONE
                    binding.cardRegister.visibility = View.VISIBLE
                    binding.logout.visibility = View.GONE
                    lifecycleScope.launchWhenStarted {
                        val result = authRepository.deleteAccount()
                        result.onSuccess {
                            findNavController().navigate(
                                R.id.registerScreen,
                                null,
                                androidx.navigation.navOptions {
                                    popUpTo(R.id.profileScreen) { inclusive = true }
                                }
                            )
                        }
                        result.onFailure {
                            requireActivity().showNotification(
                                it.message ?: "Ошибка удаления",
                                NotificationType.ERROR
                            )
                        }
                    }
                }
                .setNegativeButton("Отмена", null)
                .show()
        }

    }

}
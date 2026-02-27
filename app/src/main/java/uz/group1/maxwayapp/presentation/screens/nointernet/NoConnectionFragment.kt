package uz.group1.maxwayapp.presentation.screens.nointernet

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.leeson_network.utils.NetworkMonitor
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.databinding.ScreenNoConnectionBinding
import uz.group1.maxwayapp.presentation.screens.base_fragment.BaseFragment
import uz.group1.maxwayapp.presentation.screens.base_fragment.SystemBarConfig
import uz.group1.maxwayapp.presentation.screens.base_fragment.SystemBarIconStyle
import uz.group1.maxwayapp.utils.GlobalVariables
import uz.group1.maxwayapp.utils.NotificationType
import uz.group1.maxwayapp.utils.showNotification

class NoConnectionFragment: BaseFragment(R.layout.screen_no_connection) {

    private val binding by viewBinding(ScreenNoConnectionBinding::bind)
    private lateinit var networkMonitor: NetworkMonitor

    override val systemBarConfig = SystemBarConfig(
        statusBarIcons = SystemBarIconStyle.DARK_ICONS,
        navigationBarIcons = SystemBarIconStyle.DARK_ICONS,
        fullscreen = false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        networkMonitor = NetworkMonitor(requireContext().applicationContext)

        val message = arguments?.getString("message", "")?: ""
        if (message.isNotEmpty()){
            binding.textTitle.text = message
        }

        binding.btnRetry.setOnClickListener {
            checkConnection()
        }

        GlobalVariables.stateVisibilityBottomNav.postValue(false)

    }

    private fun checkConnection() {
        if (networkMonitor.isConnected()) {
            findNavController().popBackStack()
        } else {
            requireActivity().showNotification("Internet yo'q", NotificationType.ERROR)
        }
    }
}
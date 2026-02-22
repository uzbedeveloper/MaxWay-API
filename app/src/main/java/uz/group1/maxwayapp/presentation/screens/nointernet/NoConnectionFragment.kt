package uz.group1.maxwayapp.presentation.screens.nointernet

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.leeson_network.utils.NetworkMonitor
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.databinding.ScreenNoConnectionBinding
import uz.group1.maxwayapp.utils.NotificationType
import uz.group1.maxwayapp.utils.showNotification

class NoConnectionFragment: Fragment(R.layout.screen_no_connection) {

    private val binding by viewBinding(ScreenNoConnectionBinding::bind)
    private lateinit var networkMonitor: NetworkMonitor

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        networkMonitor = NetworkMonitor(requireContext().applicationContext)

        binding.btnRetry.setOnClickListener {
            checkConnection()
        }
    }

    private fun checkConnection() {
        if (networkMonitor.isConnected()) {
            findNavController().popBackStack()
        } else {
            requireActivity().showNotification("Internet yo'q", NotificationType.ERROR)
        }
    }
}
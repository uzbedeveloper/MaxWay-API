package uz.group1.maxwayapp.presentation.screens.notifications

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.leeson_network.utils.NetworkMonitor
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.databinding.ScreenNotificationBinding
import uz.group1.maxwayapp.presentation.adapters.NotificationsAdapter
import uz.group1.maxwayapp.utils.NotificationType
import uz.group1.maxwayapp.utils.showNotification

class NotificationScreen: Fragment(R.layout.screen_notification) {

    private lateinit var networkMonitor: NetworkMonitor
    private val binding by viewBinding(ScreenNotificationBinding::bind)
    private val viewModel: NotificationsViewModel by viewModels<NotificationsViewModelImpl>{NotificationsViewModelFactory()}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        networkMonitor = NetworkMonitor(requireContext().applicationContext)

        checkConnection()
        setUpClick()
        setUpObservers()

    }

    private fun setUpObservers() {
        viewModel.notificationsLiveData.observe(viewLifecycleOwner){ list->
            binding.rv.adapter = NotificationsAdapter(list)
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner){
            requireActivity().showNotification(it, NotificationType.ERROR)
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner){
            binding.loader.isVisible = it
        }

    }

    private fun setUpClick() {
        binding.btnRetry.setOnClickListener {
            checkConnection()
        }
    }

    private fun checkConnection() {
        if (networkMonitor.isConnected()) {
            binding.boxNoInternet.visibility = View.GONE
            binding.rv.visibility = View.VISIBLE

            viewModel.loadNotifications()
        } else {
            binding.boxNoInternet.visibility = View.VISIBLE
            binding.rv.visibility = View.GONE
        }
    }
}
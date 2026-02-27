package uz.group1.maxwayapp.presentation.screens.splash

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.data.ApiClient
import uz.group1.maxwayapp.data.repository_impl.ProductRepositoryImpl
import uz.group1.maxwayapp.presentation.screens.base_fragment.BaseFragment
import uz.group1.maxwayapp.presentation.screens.base_fragment.SystemBarConfig
import uz.group1.maxwayapp.presentation.screens.base_fragment.SystemBarIconStyle
import uz.group1.maxwayapp.utils.GlobalVariables
import uz.group1.maxwayapp.utils.NotificationType
import uz.group1.maxwayapp.utils.showNotification

class SplashFragment : BaseFragment(R.layout.screen_splash) {

    override val applyBottomInset: Boolean = false
    override val applyTopInset: Boolean = false

    override val systemBarConfig = SystemBarConfig(
        statusBarIcons = SystemBarIconStyle.LIGHT_ICONS,
        navigationBarIcons = SystemBarIconStyle.LIGHT_ICONS,
        fullscreen = true
    )

    private val repository by lazy { ProductRepositoryImpl.getInstance() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkServerAndNavigate()
        GlobalVariables.stateVisibilityBottomNav.postValue(false)
    }

    private fun checkServerAndNavigate() {
        viewLifecycleOwner.lifecycleScope.launch {
            val startTime = System.currentTimeMillis()

            val healthCheck = repository.getCategories()

            val elapsedTime = System.currentTimeMillis() - startTime
            if (elapsedTime < 1500) delay(1500 - elapsedTime)

            if (healthCheck.isSuccess) {
                findNavController().navigate(R.id.action_splashFragment_to_homeScreen)
            } else {
                val message = "500 - Server bilan muammo"
                requireActivity().showNotification(message, NotificationType.ERROR)
                findNavController().navigate(R.id.noConnectionFragment, bundleOf("message" to message))
            }
        }
    }
}
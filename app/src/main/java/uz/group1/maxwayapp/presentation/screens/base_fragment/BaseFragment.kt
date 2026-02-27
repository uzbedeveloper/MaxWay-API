package uz.group1.maxwayapp.presentation.screens.base_fragment

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment

abstract class BaseFragment(
    layoutRes: Int
) : Fragment(layoutRes) {

    open val systemBarConfig: SystemBarConfig? = null

    open val applyTopInset: Boolean = true
    open val applyBottomInset: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                if (applyTopInset) systemBars.top else 0,
                systemBars.right,
                if (applyBottomInset) systemBars.bottom else 0
            )

            insets
        }
    }

    override fun onResume() {
        super.onResume()
        applySystemBarConfig()
    }

    private fun applySystemBarConfig() {
        val config = systemBarConfig ?: return

        val window = requireActivity().window
        val controller = WindowCompat.getInsetsController(window, window.decorView)

        controller.isAppearanceLightStatusBars =
            config.statusBarIcons == SystemBarIconStyle.DARK_ICONS

        controller.isAppearanceLightNavigationBars =
            config.navigationBarIcons == SystemBarIconStyle.DARK_ICONS

        config.statusBarColorRes?.let {
            window.statusBarColor = ContextCompat.getColor(requireContext(), it)
        }

        config.navigationBarColorRes?.let {
            window.navigationBarColor = ContextCompat.getColor(requireContext(), it)
        }

        if (config.fullscreen) {
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } else {
            controller.show(WindowInsetsCompat.Type.systemBars())
        }
    }
}
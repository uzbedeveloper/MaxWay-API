package uz.group1.maxwayapp.presentation.screens.base_fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment

abstract class BaseFragment(
    layoutRes: Int
) : Fragment(layoutRes) {

    open val applyBottomInset: Boolean = true
    open val applyTopInset: Boolean = true
    open val isFullscreen: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isFullscreen) {
            enableFullscreen()
        } else {
            disableFullscreen()
        }

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
    private fun enableFullscreen() {
        val window = requireActivity().window
        val controller = WindowCompat.getInsetsController(window, window.decorView)

        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }

    private fun disableFullscreen() {
        val window = requireActivity().window
        val controller = WindowCompat.getInsetsController(window, window.decorView)

        controller.show(WindowInsetsCompat.Type.systemBars())
    }
    override fun onDestroyView() {
        disableFullscreen()
        super.onDestroyView()
    }
}
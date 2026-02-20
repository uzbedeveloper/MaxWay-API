package uz.group1.maxwayapp.utils

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

/**
 * Universal Custom Snackbar
 * @param message foydalanuvchiga habar
 * @param isSuccess snackbar foni qizil yoki yashil chiqishini taminlaydi
 *
 * @author Murodxonov
 */
fun Fragment.showNotification(message: String, isSuccess: Boolean = false) {
    val rootView = requireActivity().findViewById<ViewGroup>(android.R.id.content)

    val bannerView = TextView(requireContext()).apply {
        text = message
        setTextColor(Color.WHITE)
        textSize = 16f
        gravity = Gravity.CENTER
        setPadding(40, 30, 40, 30)

        val startColor = if (isSuccess) "#00A63E" else "#F54900"
        val endColor = if (isSuccess) "#009966" else "#E7000B"

        background = GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            intArrayOf(Color.parseColor(startColor), Color.parseColor(endColor))
        ).apply {
            cornerRadius = 24f
        }
    }

    val params = FrameLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    ).apply {
        gravity = Gravity.TOP
        topMargin = 150
        leftMargin = 60
        rightMargin = 60
    }

    rootView.addView(bannerView, params)

    bannerView.alpha = 0f
    bannerView.translationY = -100f
    bannerView.animate()
        .alpha(1f)
        .translationY(0f)
        .setDuration(600)
        .withEndAction {
            bannerView.animate()
                .alpha(0f)
                .translationY(-100f)
                .setStartDelay(2500)
                .setDuration(700)
                .withEndAction { rootView.removeView(bannerView) }
                .start()
        }
        .start()
}
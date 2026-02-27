package uz.group1.maxwayapp.presentation.screens.base_fragment

data class SystemBarConfig(
    val statusBarIcons: SystemBarIconStyle,
    val navigationBarIcons: SystemBarIconStyle,
    val fullscreen: Boolean = false,
    val statusBarColorRes: Int? = null,
    val navigationBarColorRes: Int? = null
)
package uz.group1.maxwayapp

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import uz.gita.leeson_network.utils.NetworkConnectionCallback
import uz.gita.leeson_network.utils.NetworkMonitor
import uz.group1.maxwayapp.databinding.ActivityMainBinding
import uz.group1.maxwayapp.presentation.screens.home.cart_bottomsheet.CartBottomSheet
import uz.group1.maxwayapp.utils.GlobalVariables
import uz.group1.maxwayapp.utils.NotificationType
import uz.group1.maxwayapp.utils.showNotification

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var networkMonitor: NetworkMonitor
    lateinit var navController: NavController

    private val viewModel: MainViewModel by viewModels { MainViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        networkMonitor = NetworkMonitor(applicationContext)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                v.paddingBottom
            )

            insets
        }

        setUpBottomNav()
        networkMonitor()
        setUpObservers()
    }

    private fun setUpObservers() {
        GlobalVariables.stateVisibilityBottomNav.observe(this){
            binding.bottomNav.isVisible = it

        }

        lifecycleScope.launch {
            viewModel.totalCartCount.collect { count ->
                updateCartBadge(count)
            }
        }
    }

    private fun setUpBottomNav() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navContainerView) as NavHostFragment

        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.filialScreen -> {
                    binding.bottomNav.visibility = View.GONE
                }
                R.id.notificationScreen ->{
                    binding.bottomNav.visibility = View.GONE
                }
                R.id.searchScreen -> {
                    binding.bottomNav.visibility = View.GONE
                }
                R.id.registerScreen ->{
                    binding.bottomNav.visibility = View.GONE
                }
                R.id.verifyScreen -> {
                    binding.bottomNav.visibility = View.GONE

                }
                R.id.createUserScreen ->{
                    binding.bottomNav.visibility = View.GONE
                }

                R.id.addAddressScreen -> {
                    binding.bottomNav.visibility = View.GONE
                }

                else -> {
                    binding.bottomNav.visibility = View.VISIBLE
                }
            }
        }
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.cartDialog -> {
                    val bottomSheet = CartBottomSheet()
                    bottomSheet.show(supportFragmentManager, "CustomBottomSheet")
                    false
                }
                else -> {
                    NavigationUI.onNavDestinationSelected(item, navController)
                    true
                }
            }
        }
    }

    private fun networkMonitor() {
        networkMonitor.startMonitoring(object : NetworkConnectionCallback {
            override fun onNetworkAvailable() {
                runOnUiThread {
                    if (navController.currentDestination?.id != R.id.splashFragment){
                        GlobalVariables.stateVisibilityBottomNav.postValue(true)
                    }

                    if (navController.currentDestination?.id == R.id.noConnectionFragment) {
                        showNotification("Internet tiklandi", NotificationType.SUCCESS)

                        navController.popBackStack()
                    }
                }
            }

            override fun onNetworkLost() {
                runOnUiThread {
                    showNotification("Internet yo'q", NotificationType.ERROR)
                    navController.navigate(R.id.noConnectionFragment)
                    GlobalVariables.stateVisibilityBottomNav.postValue(false)

                }
            }

            override fun onNetworkLosing() {
                runOnUiThread {
                    showNotification("Internet signal yo'qolmoqda", NotificationType.WARNING)
                }
            }

            override fun onNetworkUnavailable() {
                runOnUiThread {
                    showNotification("Internet mavjud emas", NotificationType.INFO)
                    navController.navigate(R.id.noConnectionFragment)
                    GlobalVariables.stateVisibilityBottomNav.postValue(false)

                }
            }
        })
    }

    private fun updateCartBadge(count: Int) {
        val bottomNav = binding.bottomNav

        if (count > 0) {
            val badge = bottomNav.getOrCreateBadge(R.id.cartDialog)
            badge.isVisible = true
            badge.number = count
            badge.backgroundColor = Color.RED
            badge.badgeTextColor = Color.WHITE
        } else {
            bottomNav.removeBadge(R.id.cartDialog)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        networkMonitor.stopMonitoring()
    }
}
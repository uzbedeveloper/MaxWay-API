package uz.group1.maxwayapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import uz.gita.leeson_network.utils.NetworkConnectionCallback
import uz.gita.leeson_network.utils.NetworkMonitor
import uz.group1.maxwayapp.databinding.ActivityMainBinding
import uz.group1.maxwayapp.utils.NotificationType
import uz.group1.maxwayapp.utils.showNotification

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var networkMonitor: NetworkMonitor
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navContainerView) as NavHostFragment
        navController = navHostFragment.navController

        networkMonitor = NetworkMonitor(this)
        setupNetworkMonitoring()
    }

    private fun setupNetworkMonitoring() {
        networkMonitor.startMonitoring(object : NetworkConnectionCallback {
            override fun onNetworkAvailable() {
                runOnUiThread {
                    if (navController.currentDestination?.id == R.id.noConnectionFragment) {
                        showNotification("Internet tiklandi", NotificationType.SUCCESS)

                        navController.navigate(R.id.splashFragment2, null, navOptions {
                            popUpTo(R.id.noConnectionFragment) { inclusive = true }
                        })
                    }
                }
            }

            override fun onNetworkLost() {
                runOnUiThread {
                    showNotification("Internet yo'q", NotificationType.ERROR)
                    if (navController.currentDestination?.id != R.id.noConnectionFragment) {
                        navController.navigate(R.id.noConnectionFragment)
                    }
                }
            }

            override fun onNetworkLosing() {}
            override fun onNetworkUnavailable() {
                onNetworkLost()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        networkMonitor.stopMonitoring()
    }
}
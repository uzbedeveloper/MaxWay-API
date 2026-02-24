package uz.gita.leeson_network.utils

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity

/*
*
* bu kod maqsadi network connection ishlatishni ko'rsatish uchun
*
* */

class NetworkCheckActivity : ComponentActivity() {
    private lateinit var networkMonitor: NetworkMonitor
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        networkMonitor = NetworkMonitor(applicationContext)

        networkMonitor.startMonitoring(object : NetworkConnectionCallback {
            override fun onNetworkAvailable() {
                runOnUiThread {
                    // UI yangilash
                    Toast.makeText(this@NetworkCheckActivity, "Online", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNetworkLost() {
                runOnUiThread {
                    // UI yangilash
                    Toast.makeText(this@NetworkCheckActivity, "Connection Lost", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNetworkLosing() {
            }

            override fun onNetworkUnavailable() {
                runOnUiThread {
                    Toast.makeText(this@NetworkCheckActivity, "Offline", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    
    override fun onDestroy() {
        super.onDestroy()
        networkMonitor.stopMonitoring()
    }
    
    // Bir martalik tekshirish uchun
    private fun checkConnection() {
        if (networkMonitor.isConnected()) {
            // Internet bor
        } else {
            // Internet yo'q
        }

        // Qaysi tarmoqqa ulanganligi
        when {
            networkMonitor.isWifiConnected() -> { /* WiFi */ }
            networkMonitor.isCellularConnected() -> { /* Mobil */ }
            else -> { /* Ulanish yo'q */ }
        }
    }
}
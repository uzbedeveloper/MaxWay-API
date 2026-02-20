package uz.gita.leeson_network.utils

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    private lateinit var networkMonitor: NetworkMonitor
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        networkMonitor = NetworkMonitor(applicationContext)

        networkMonitor.startMonitoring(object : NetworkConnectionCallback {
            override fun onNetworkAvailable() {
                runOnUiThread {
                    Log.d("TTT","Internet mavjud")
                    // UI yangilash
                    Toast.makeText(this@MainActivity, "Online", Toast.LENGTH_SHORT).show()
                }
            }
            
            override fun onNetworkLost() {
                runOnUiThread {
                    Log.d("TTT"," Internet yo'qoldi")
                    // UI yangilash
                    Toast.makeText(this@MainActivity, "Connection Lost", Toast.LENGTH_SHORT).show()
                }
            }
            
            override fun onNetworkLosing() {
                runOnUiThread {
                    Log.d("TTT","Internet yo'qolmoqda")
                }
            }
            
            override fun onNetworkUnavailable() {
                runOnUiThread {
                    Log.d("TTT"," Internet yo'q")
                    Toast.makeText(this@MainActivity, "Offline", Toast.LENGTH_SHORT).show()
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
            Log.d("TTT","Internet bor")
        } else {
            Log.d("TTT","Internet yo'q")
        }
        
        // Qaysi tarmoqqa ulanganligi
        when {
            networkMonitor.isWifiConnected() -> Log.d("TTT","WiFi orqali ulangan")
            networkMonitor.isCellularConnected() -> Log.d("TTT","Mobil internet orqali ulangan")
            else -> Log.d("TTT","Ulanish yo'q")
        }
    }
}
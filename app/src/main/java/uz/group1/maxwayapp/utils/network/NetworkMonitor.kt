package uz.gita.leeson_network.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest

class NetworkMonitor(private val context: Context) {
    
    private val connectivityManager = 
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    
    private var callback: NetworkConnectionCallback? = null
    private var isMonitoring = false
    
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            callback?.onNetworkAvailable()
        }
        
        override fun onLosing(network: Network, maxMsToLive: Int) {
            callback?.onNetworkLosing()
        }
        
        override fun onLost(network: Network) {
            callback?.onNetworkLost()
        }
        
        override fun onUnavailable() {
            callback?.onNetworkUnavailable()
        }
    }

    //Monitoringni boshlash
    fun startMonitoring(callback: NetworkConnectionCallback) {
        if (isMonitoring) return
        
        this.callback = callback
        
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        
        connectivityManager.registerNetworkCallback(request, networkCallback)
        isMonitoring = true
        
        // Hozirgi holatni tekshirish
        if (isConnected()) {
            callback.onNetworkAvailable()
        } else {
            callback.onNetworkUnavailable()
        }
    }

    // Monitoringni to'xtatish
    fun stopMonitoring() {
        if (!isMonitoring) return

        try {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        isMonitoring = false
        callback = null
    }

    //Internet borligini tekshirish (bir martalik)
    fun isConnected(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
               capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }

    //WiFi ga ulanganligini tekshirish
    fun isWifiConnected(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }

    // Mobil internetga ulanganligini tekshirish
    fun isCellularConnected(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    }
}
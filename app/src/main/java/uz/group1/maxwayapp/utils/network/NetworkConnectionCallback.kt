package uz.gita.leeson_network.utils

interface NetworkConnectionCallback {
    fun onNetworkAvailable()
    fun onNetworkLost()
    fun onNetworkLosing()
    fun onNetworkUnavailable()
}
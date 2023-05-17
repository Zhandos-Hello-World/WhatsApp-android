package kz.tinkoff.homework_2.data.network

import android.net.ConnectivityManager.NetworkCallback
import android.net.Network


class NetworkStatusListener(private val internetConnectedListener: (Boolean) -> Unit) :
    NetworkCallback() {

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        internetConnectedListener(true)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        internetConnectedListener(false)
    }

}

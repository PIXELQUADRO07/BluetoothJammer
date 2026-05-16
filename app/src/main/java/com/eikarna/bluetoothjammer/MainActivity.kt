package com.eikarna.bluetoothjammer

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import api.BluetoothDeviceInfo
import api.ScanNearbyDevices
import com.eikarna.bluetoothjammer.ui.components.CyberButton
import com.eikarna.bluetoothjammer.ui.components.CyberCard
import com.eikarna.bluetoothjammer.ui.components.GlitchText
import com.eikarna.bluetoothjammer.ui.theme.CyberPrimary
import com.eikarna.bluetoothjammer.ui.theme.CyberpunkTheme
import com.eikarna.bluetoothjammer.ui.theme.CyberSecondary

class MainActivity : ComponentActivity() {

    private val scanner = ScanNearbyDevices.getInstance()

    companion object {
        private const val PERMISSION_REQUEST_CODE = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        checkPermissionsAndStartScanning()

        setContent {
            CyberpunkTheme {
                MainScreen(
                    scanner = scanner,
                    onDeviceClick = { device ->
                        val intent = Intent(this, AttackActivity::class.java).apply {
                            putExtra("DEVICE_NAME", device.name)
                            putExtra("ADDRESS", device.address)
                        }
                        startActivity(intent)
                    }
                )
            }
        }
    }

    private fun checkPermissionsAndStartScanning() {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            arrayOf(
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } else {
            arrayOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }

        if (permissions.all { ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED }) {
            scanner.startScanning(this)
        } else {
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            scanner.startScanning(this)
        }
    }

    override fun onResume() {
        super.onResume()
        scanner.startScanning(this)
    }

    override fun onPause() {
        super.onPause()
        scanner.stopScanning(this)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    scanner: ScanNearbyDevices,
    onDeviceClick: (BluetoothDeviceInfo) -> Unit
) {
    val devices by scanner.discoveredDevices.collectAsState()
    var isScanning by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { GlitchText("BLUETOOTH JAMMER", color = CyberPrimary) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            CyberCard(
                modifier = Modifier.fillMaxWidth(),
                borderColor = CyberPrimary
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("SYSTEM STATUS", color = CyberPrimary.copy(alpha = 0.7f), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text(if (isScanning) "SCANNING..." else "IDLE", color = CyberPrimary, fontSize = 18.sp)
                    }
                    CyberButton(
                        text = if (isScanning) "STOP SCAN" else "START SCAN",
                        onClick = { isScanning = !isScanning },
                        color = CyberPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("DISCOVERED TARGETS", color = CyberPrimary, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(devices.toList()) { device ->
                    DeviceItem(device = device, onClick = { onDeviceClick(device) })
                }
            }
        }
    }
}

@Composable
fun DeviceItem(device: BluetoothDeviceInfo, onClick: () -> Unit) {
    CyberCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        borderColor = CyberSecondary
    ) {
        Column {
            Text(device.name, color = CyberPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(device.address, color = CyberPrimary.copy(alpha = 0.5f), fontSize = 14.sp)
        }
    }
}

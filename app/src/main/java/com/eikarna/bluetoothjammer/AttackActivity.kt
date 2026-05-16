package com.eikarna.bluetoothjammer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.L2capFloodAttack
import com.eikarna.bluetoothjammer.api.AttackManager
import com.eikarna.bluetoothjammer.ui.components.CyberButton
import com.eikarna.bluetoothjammer.ui.components.CyberCard
import com.eikarna.bluetoothjammer.ui.components.GlitchText
import com.eikarna.bluetoothjammer.ui.theme.CyberError
import com.eikarna.bluetoothjammer.ui.theme.CyberNeonGreen
import com.eikarna.bluetoothjammer.ui.theme.CyberPrimary
import com.eikarna.bluetoothjammer.ui.theme.CyberpunkTheme
import com.eikarna.bluetoothjammer.ui.theme.CyberSecondary
import androidx.compose.ui.platform.LocalContext
import android.content.Context
import kotlinx.coroutines.launch

class AttackActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val deviceName = intent.getStringExtra("DEVICE_NAME") ?: "Unknown"
        val address = intent.getStringExtra("ADDRESS") ?: "Unknown"

        setContent {
            CyberpunkTheme {
                AttackScreen(
                    deviceName = deviceName,
                    address = address,
                    onBack = { finish() }
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AttackManager.stopAllAttacks()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttackScreen(
    deviceName: String,
    address: String,
    onBack: () -> Unit
) {
    var threads by remember { mutableStateOf(8f) }
    var isAttacking by remember { mutableStateOf(false) }
    val logs = remember { mutableStateListOf<String>() }
    val listState = rememberLazyListState()
    val context = LocalContext.current

    LaunchedEffect(logs.size) {
        if (logs.isNotEmpty()) {
            listState.animateScrollToItem(logs.size - 1)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { GlitchText("ATTACK PROTOCOL", color = CyberError) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("< BACK", color = CyberPrimary)
                    }
                }
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
                Text("TARGET ACQUIRED", color = CyberPrimary.copy(alpha = 0.7f), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Text(deviceName, color = CyberPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(address, color = CyberPrimary.copy(alpha = 0.5f), fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            CyberCard(
                modifier = Modifier.fillMaxWidth(),
                borderColor = CyberPrimary
            ) {
                Text("ATTACK INTENSITY: ${threads.toInt()}", color = CyberPrimary, fontWeight = FontWeight.Bold)
                Slider(
                    value = threads,
                    onValueChange = { if (!isAttacking) threads = it },
                    valueRange = 1f..32f,
                    colors = SliderDefaults.colors(
                        thumbColor = CyberPrimary,
                        activeTrackColor = CyberPrimary,
                        inactiveTrackColor = CyberPrimary.copy(alpha = 0.2f)
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            CyberButton(
                text = if (isAttacking) "TERMINATE ATTACK" else "INITIALIZE ATTACK",
                onClick = {
                    if (isAttacking) {
                        AttackManager.stopAllAttacks()
                        isAttacking = false
                        logs.add("[SYSTEM] Attack terminated.")
                    } else {
                        isAttacking = true
                        logs.add("[SYSTEM] Initializing flood attack on $address...")
                        AttackManager.startAttack(address, threads.toInt()) {
                            L2capFloodAttack(address).performAttack(context) { message ->
                                logs.add(message)
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                color = CyberPrimary
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("LOG_STREAM", color = CyberPrimary, fontWeight = FontWeight.Bold)
            CyberCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                borderColor = CyberPrimary
            ) {
                LazyColumn(state = listState) {
                    items(logs) { log ->
                        Text(
                            text = log,
                            color = if (log.contains("[SYSTEM]")) CyberPrimary.copy(alpha = 0.7f) else CyberPrimary,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}

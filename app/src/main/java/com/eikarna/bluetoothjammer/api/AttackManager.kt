package com.eikarna.bluetoothjammer.api

import kotlinx.coroutines.*
import java.util.concurrent.ConcurrentHashMap

object AttackManager {
    private val activeAttacks = ConcurrentHashMap<String, MutableList<Job>>()
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    var isAttacking = false
        private set

    fun startAttack(address: String, threads: Int, attackLogic: suspend () -> Unit) {
        isAttacking = true
        val jobs = activeAttacks.getOrPut(address) { mutableListOf() }
        repeat(threads) {
            val job = scope.launch {
                while (isActive && isAttacking) {
                    attackLogic()
                }
            }
            jobs.add(job)
        }
    }

    fun stopAllAttacks() {
        isAttacking = false
        activeAttacks.values.forEach { jobs ->
            jobs.forEach { it.cancel() }
        }
        activeAttacks.clear()
    }
}

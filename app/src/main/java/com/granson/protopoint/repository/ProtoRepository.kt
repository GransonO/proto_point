package com.granson.protopoint.repository

import com.granson.protopoint.interfaces.ProtoService
import com.granson.protopoint.models.ProtoData
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class ProtoRepository @Inject constructor(
    private val protoService: ProtoService
) {
    suspend fun getTheOrders(): List<ProtoData> {
        return try {
            println("-------------> ${protoService}")
            protoService.getOrders()
        } catch (e: Exception) {
            // Error handled
            println("--error-----------> $e")
            return listOf();
        }
    }
}
package com.granson.protopoint.interfaces

import com.granson.protopoint.models.ProtoData
import retrofit2.http.GET

interface ProtoService {

    @GET("/api/v1/orders.json")
    suspend fun getOrders(): List<ProtoData>
}
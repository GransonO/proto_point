package com.granson.protopoint.interfaces

import com.granson.protopoint.models.ProtoData

interface ResponseListener {
    fun failed(message: String)
}
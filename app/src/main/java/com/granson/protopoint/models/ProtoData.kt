package com.granson.protopoint.models

data class ProtoData(
    val batchNumber: String,
    val createdBy: String,
    val creatorUserEmail: String,
    val customerCode: String,
    val customerName: String,
    val dateCreated: String,
    val dateModified: String,
    val deliveryPointCode: String,
    val deliveryPointName: String,
    val id: String,
    val modifiedBy: String,
    val modifierUserEmail: Any,
    val orderTotal: Double,
    val remarks: String,
    val salesAreaCode: String,
    val salesAreaName: String,
    val status: String,
    val userPhoneNumber0: String
)
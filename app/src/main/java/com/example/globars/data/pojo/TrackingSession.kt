package com.example.globars.data.pojo

import com.example.globars.data.pojo.units.TrackingUnit

data class TrackingSession(
    val createdAt: String,
    val drivers: List<Any>,
    val geozones: List<Any>,
    val groups: List<Any>,
    val id: String,
    val trackingSettings: Any,
    val trailers: List<Any>,
    val units: List<TrackingUnit>,
    val updatedAt: String,
    val userId: String
)
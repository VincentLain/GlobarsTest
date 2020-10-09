package com.example.globars.ui.maps

import com.example.globars.data.pojo.units.TrackingUnit

data class TrackingUnitsResult(
    val success: List<TrackingUnit>? = null,
    val error: String = ""
)
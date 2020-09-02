package com.jetbrains.handson.mpp.mobile

import kotlinx.serialization.Serializable

@Serializable
data class FareSearchResponse(
    val outboundJourneys: List<Journey>
)

@Serializable
data class Journey(
    val journeyId: String,
    val originStation: Station,
    val destinationStation: Station,
    val departureTime: String,
    val departureRealTime: String? = null,
    val arrivalTime: String,
    val arrivalRealTime: String? = null,
    val status: String,
    val journeyDurationInMinutes: Int
)

@Serializable
data class Station(
    val displayName: String,
    val crs: String,
    val nlc: String
)






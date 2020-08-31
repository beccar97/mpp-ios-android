package com.jetbrains.handson.mpp.mobile

data class Station(
    val stationCode: String,
    val stationName: String
)

val stations: List<Station> = listOf(
    Station("KGX", "Kings Cross"),
    Station("PBO", "Peterborough"),
    Station("EDB", "Edinburgh"),
    Station("OXF", "Oxford"),
    Station("MYB", "Marylebone")
)
package com.jetbrains.handson.mpp.mobile

expect fun platformName(): String

val stations: List<Station> = listOf(
    Station("Kings Cross", "KGX", "6121"),
    Station("Peterborough", "PBO", "6133"),
    Station("Edinburgh", "EDB", "9328"),
    Station("Oxford", "OXF", "3115"),
    Station("Marylebone", "MYB", "1475")
)
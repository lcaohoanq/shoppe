package com.lcaohoanq.authservice.domains.loginhistory

data class GeoLocation(
    val ip: String,
    val country: String,
    val city: String,
    val lat: Double,
    val lon: Double
)

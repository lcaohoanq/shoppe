package com.lcaohoanq.authservice.domains.loginhistory

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

//import org.json.JSONObject
//import java.net.HttpURLConnection
//import java.net.URL

@Service
class GeoLocationService(
    private val webClient: WebClient
) {

    /*    fun getLocationFromIp(ip: String): GeoLocation {
            val url = URL("http://ip-api.com/json/$ip")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val response = connection.inputStream.bufferedReader().readText()
            val json = JSONObject(response)

            if (json.getString("status") != "success") {
                throw RuntimeException("Unable to retrieve location for IP: $ip")
            }

            return GeoLocation(
                ip = ip,
                country = json.optString("country", "Unknown"),
                city = json.optString("city", "Unknown"),
                lat = json.optDouble("lat", 0.0),
                lon = json.optDouble("lon", 0.0)
            )
        }*/

    fun getLocationFromIp(ip: String): GeoLocation {
        val response = webClient.get()
            .uri("/json/{ip}", ip)
            .retrieve()
            .bodyToMono(IpApiResponse::class.java)
            .block()  // blocking ở đây vì app bạn là Spring MVC, nếu WebFlux thì dùng `.subscribe()` hoặc `.flatMap`...

        if (response?.status != "success") {
            throw RuntimeException("Unable to retrieve location for IP: $ip")
        }

        return GeoLocation(
            ip = ip,
            country = response.country ?: "Unknown",
            city = response.city ?: "Unknown",
            lat = response.lat ?: 0.0,
            lon = response.lon ?: 0.0
        )
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class IpApiResponse(
        val status: String?,
        val country: String?,
        val city: String?,
        val lat: Double?,
        val lon: Double?
    )


}
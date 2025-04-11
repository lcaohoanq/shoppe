package com.lcaohoanq.authservice.domains.loginhistory

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/ip")
class IpController(
    private val geoLocationService: GeoLocationService
) {
    @GetMapping("/location")
    fun getLocation(@RequestParam ip: String): ResponseEntity<GeoLocation> {
        val location = geoLocationService.getLocationFromIp(ip)
        return ResponseEntity.ok(location)
    }
}
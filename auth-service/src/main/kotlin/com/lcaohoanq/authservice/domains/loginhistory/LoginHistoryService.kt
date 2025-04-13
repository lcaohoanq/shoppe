package com.lcaohoanq.authservice.domains.loginhistory

import com.lcaohoanq.authservice.domains.user.User
import com.lcaohoanq.authservice.repositories.LoginHistoryRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class LoginHistoryService(
    private val loginHistoryRepository: LoginHistoryRepository,
    private val geoLocationService: GeoLocationService
) : ILoginHistoryService {

    private val log = KotlinLogging.logger {}

    override fun recordLogin(user: User, ipAddress: String, userAgent: String) {
        val location = geoLocationService.getLocationFromIp(ipAddress)
        val loginEntry = LoginHistory(
            user = user,
            ipAddress = ipAddress,
            loginAt = LocalDateTime.now(),
            location = "${location.city}, ${location.country}",
            success = true,
            userAgent = userAgent
        )
        loginHistoryRepository.save(loginEntry)

        log.info("User ${user.id} logged in from IP=$ipAddress, UA=$userAgent, Location=${location.city}, ${location.country}")
    }
}
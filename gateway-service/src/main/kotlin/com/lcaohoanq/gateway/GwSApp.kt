package com.lcaohoanq.gateway

import io.github.hoangclw.kotlinbrowserlauncher.openHomePage
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class GatewayServiceApplication

fun main(args: Array<String>) {
    val context = runApplication<GatewayServiceApplication>(*args)
    val env = context.environment
    val activeProfiles = env.activeProfiles

    if (!activeProfiles.contains("docker")) {
        openHomePage("http://localhost:4003/swagger-ui.html")
    }
}

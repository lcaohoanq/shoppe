package com.lcaohoanq.authservice

import io.github.hoangclw.kotlinbrowserlauncher.openHomePage
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCaching
@EnableScheduling
class AuthServiceApplication

fun main(args: Array<String>) {
    val context = runApplication<AuthServiceApplication>(*args)
    val env = context.environment
    val activeProfiles = env.activeProfiles

    if (!activeProfiles.contains("docker")) {
        openHomePage("http://localhost:4006/swagger-ui/index.html")
    }
}

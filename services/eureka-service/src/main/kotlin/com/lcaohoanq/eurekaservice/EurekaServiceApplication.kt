package com.lcaohoanq.eurekaservice

import io.github.hoangclw.kotlinbrowserlauncher.openHomePage
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@SpringBootApplication
@EnableEurekaServer
class EurekaServiceApplication

fun main(args: Array<String>) {
    runApplication<EurekaServiceApplication>(*args)
    openHomePage("http://localhost:8761")
}

package com.lcaohoanq.eurekaservice

import com.lcaohoanq.common.utils.WebUtil
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@SpringBootApplication
@EnableEurekaServer
class EurekaServiceApplication

fun main(args: Array<String>) {
    runApplication<EurekaServiceApplication>(*args)
    WebUtil.openHomePage("http://localhost:8761/") // Open Eureka dashboard
}

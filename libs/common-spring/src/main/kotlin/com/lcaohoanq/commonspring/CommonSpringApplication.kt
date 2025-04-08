package com.lcaohoanq.commonspring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class CommonSpringApplication

fun main(args: Array<String>) {
    runApplication<CommonSpringApplication>(*args)
}

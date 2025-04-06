package com.lcaohoanq.gateway

import com.lcaohoanq.common.utils.WebUtil
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class GatewayServiceApplication

fun main(args: Array<String>) {
    runApplication<GatewayServiceApplication>(*args)
//    WebUtil.openHomePage(WebUtil.urlListGateWay)
}

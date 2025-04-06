package com.lcaohoanq.ktservice

import com.lcaohoanq.common.utils.WebUtil
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.retry.annotation.EnableRetry
import java.util.*

@EnableCaching
@EnableRetry
@EnableDiscoveryClient
@SpringBootApplication
class KtApp

fun main(args: Array<String>) {
    // Set default timezone
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"))
    runApplication<KtApp>(*args)
//    WebUtil.openHomePage(WebUtil.urlListKotLin)
}

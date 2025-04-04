package com.lcaohoanq.assetservice

import com.lcaohoanq.common.utils.WebUtil
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AssetServiceApplication

fun main(args: Array<String>) {
    runApplication<AssetServiceApplication>(*args)
    WebUtil.openHomePage(listOf(
        "http://localhost:4001/swagger-ui/index.html",
    ))
}

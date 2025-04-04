package com.lcaohoanq.chatservice

import com.lcaohoanq.common.utils.WebUtil
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ChatServiceApplication

fun main(args: Array<String>) {
    runApplication<ChatServiceApplication>(*args)
    WebUtil.openHomePage(listOf(
        "http://localhost:4002/swagger-ui/index.html",
    ))
}

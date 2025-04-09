package org.langchain4j.apithrottling

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ApiThrottlingApplication

fun main(args: Array<String>) {
    runApplication<ApiThrottlingApplication>(*args)
}

package com.lcaohoanq.kotlinbasics

import com.lcaohoanq.kotlinbasics.utils.openHomePage
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class App

fun main(args: Array<String>) {
    runApplication<App>(*args)
    openHomePage()
}

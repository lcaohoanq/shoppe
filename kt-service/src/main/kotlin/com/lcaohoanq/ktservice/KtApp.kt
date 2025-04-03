package com.lcaohoanq.ktservice

import com.lcaohoanq.common.utils.WebUtil
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KtApp

fun main(args: Array<String>) {
    runApplication<KtApp>(*args)
    WebUtil.openHomePage()
}

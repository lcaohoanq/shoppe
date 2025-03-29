package com.lcaohoanq.kotlinbasics.cron

import com.lcaohoanq.kotlinbasics.base.BaseLogger
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class SystemLogger : BaseLogger() {

    @Scheduled(fixedRate = 5000)
    fun printMessage() {
//        log.info { "Hello, I'm a scheduled task" }
    }

}
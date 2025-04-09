package com.lcaohoanq.commonspring.configs

import feign.RetryableException
import feign.Retryer
import org.slf4j.LoggerFactory
import java.util.concurrent.TimeUnit

class CustomRetryer(
    private val period: Long = 100,
    private val maxPeriod: Long = 1000,
    private val maxAttempts: Int = 3
) : Retryer {

    private var attempt = 1
    private val logger = LoggerFactory.getLogger(CustomRetryer::class.java)

    override fun continueOrPropagate(e: RetryableException) {
        if (attempt >= maxAttempts) {
            println("❌ Max retry attempts reached ($maxAttempts). Failing with exception: ${e.message}")
            logger.error("❌ Max retry attempts reached ($maxAttempts). Failing with exception: ${e.message}")
            throw e
        }

        println("🔁 Retry attempt $attempt/${maxAttempts - 1} for request to ${e.request().url()} — Reason: ${e.message}")
        logger.warn("🔁 Retry attempt $attempt/${maxAttempts - 1} for request to ${e.request().url()} — Reason: ${e.message}")

        attempt++

        try {
            TimeUnit.MILLISECONDS.sleep(period)
        } catch (ex: InterruptedException) {
            Thread.currentThread().interrupt()
            logger.error("❌ Retry interrupted.")
            throw e
        }
    }

    override fun clone(): Retryer = CustomRetryer(period, maxPeriod, maxAttempts)
}
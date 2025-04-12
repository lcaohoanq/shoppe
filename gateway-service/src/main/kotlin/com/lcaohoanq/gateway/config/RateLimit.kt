package com.lcaohoanq.gateway.config

data class RateLimit(val capacity: Int, val refillPerSec: Int)

val rateLimitRules: Map<Regex, RateLimit> = mapOf(
    Regex("^/api/v1/auth/login") to RateLimit(3, 1), // Strict
    Regex("^/api/v1/products") to RateLimit(50, 20), // Moderate
    Regex("^/api/v1/cart") to RateLimit(20, 5),      // New example
)

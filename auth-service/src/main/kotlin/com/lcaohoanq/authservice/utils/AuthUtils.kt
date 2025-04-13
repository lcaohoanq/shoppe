package com.lcaohoanq.authservice.utils

import jakarta.servlet.http.HttpServletRequest

fun HttpServletRequest.getClientIp(): String =
    getHeader("X-Forwarded-For")?.split(",")?.firstOrNull() ?: remoteAddr

fun HttpServletRequest.getUserAgent(): String =
    getHeader("User-Agent") ?: "Unknown"

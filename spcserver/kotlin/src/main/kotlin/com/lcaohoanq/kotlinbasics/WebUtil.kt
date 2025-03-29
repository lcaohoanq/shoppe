package com.lcaohoanq.kotlinbasics

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes


object WebUtil {
    val currentRequest: HttpServletRequest
        get() = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
}

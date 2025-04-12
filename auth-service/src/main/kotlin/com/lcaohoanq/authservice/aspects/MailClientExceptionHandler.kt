package com.lcaohoanq.authservice.aspects

import mu.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Aspect
@Component
class MailClientExceptionHandler {
    private val log = KotlinLogging.logger {}

    @Around("execution(* com.lcaohoanq.authservice.clients.MailFeignClient.*(..))")
    fun handleMailClientException(joinPoint: ProceedingJoinPoint): Any? {
        return try {
            joinPoint.proceed()
        } catch (e: Exception) {
            log.error("Mail service error in method ${joinPoint.signature.name}: ${e.message}")
            null // Or appropriate default response
        }
    }
}
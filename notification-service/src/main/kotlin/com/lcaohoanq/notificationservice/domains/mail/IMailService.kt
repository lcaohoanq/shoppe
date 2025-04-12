package com.lcaohoanq.notificationservice.domains.mail

import feign.FeignException
import org.thymeleaf.context.Context

interface IMailService {
    fun sendMail(to: String, subject: String, templateName: String, context: Context)
    abstract fun ServiceUnavailable(s: String): FeignException.ServiceUnavailable
//    fun createEmailVerification(user: User): Single<User>
}

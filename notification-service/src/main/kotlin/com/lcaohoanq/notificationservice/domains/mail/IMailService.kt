package com.lcaohoanq.notificationservice.domains.mail

import org.thymeleaf.context.Context

interface IMailService {
    fun sendMail(to: String, subject: String, templateName: String, context: Context)
//    fun createEmailVerification(user: User): Single<User>
}

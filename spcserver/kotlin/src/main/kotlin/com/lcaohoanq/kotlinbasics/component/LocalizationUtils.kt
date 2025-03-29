package com.lcaohoanq.kotlinbasics.component

import com.lcaohoanq.kotlinbasics.WebUtil
import jakarta.servlet.http.HttpServletRequest
import org.springframework.context.MessageSource
import org.springframework.stereotype.Component
import java.util.Locale


@Component
class LocalizationUtils(
    private val messageSource: MessageSource,
    private val customLocaleResolver: org.springframework.web.servlet.LocaleResolver
) {

    fun getLocalizedMessage(messageKey: String, vararg params: Any?): String {
        val request: HttpServletRequest = WebUtil.currentRequest
        val locale: Locale = customLocaleResolver.resolveLocale(request)
        return messageSource.getMessage(messageKey, params, locale)
    }
}

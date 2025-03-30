package com.lcaohoanq.ktservice.custom.annotations

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiQuotable(val endpoint: String)


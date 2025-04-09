package com.lcaohoanq.common.annotations

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiQuotable(val endpoint: String)


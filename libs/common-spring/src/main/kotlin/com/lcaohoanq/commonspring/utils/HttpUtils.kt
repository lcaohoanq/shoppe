package com.lcaohoanq.commonspring.utils

import com.lcaohoanq.common.apis.MyApiResponse
import org.springframework.http.ResponseEntity

fun <T> unwrap(response: ResponseEntity<MyApiResponse<T>>): T {
    return response.body?.data ?: throw IllegalStateException("No data in response")
}
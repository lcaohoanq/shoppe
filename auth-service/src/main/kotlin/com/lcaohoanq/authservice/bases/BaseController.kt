package com.lcaohoanq.authservice.bases

import com.lcaohoanq.common.apis.MyApiResponse
import org.springframework.http.ResponseEntity

open class BaseController {
    fun <T> ok(
        message: String,
        data: T? = null,
        isSuccess: Boolean? = true,
        statusCode: Int? = 200
    ): ResponseEntity<MyApiResponse<T>> {
        return ResponseEntity.ok(
            MyApiResponse(
                message = message,
                data = data
            )
        )
    }
}

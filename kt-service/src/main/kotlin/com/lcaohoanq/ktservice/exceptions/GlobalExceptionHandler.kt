package com.lcaohoanq.ktservice.exceptions

import com.lcaohoanq.ktservice.api.ApiError
import mu.KotlinLogging
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import java.io.IOException
import java.util.function.Consumer

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = KotlinLogging.logger {}

    @ExceptionHandler(com.lcaohoanq.ktservice.exceptions.base.DataNotFoundException::class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun handleDataNotFoundException(e: com.lcaohoanq.ktservice.exceptions.base.DataNotFoundException): ApiError<Any> {
        return ApiError(
            message = "Resource not found",
            statusCode = HttpStatus.NO_CONTENT.value()
        )
    }

    @ExceptionHandler(NullPointerException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleNullPointerException(e: NullPointerException): ApiError<Any> {
        return ApiError(
            message = "Null pointer exception",
            reason = e.message,
            statusCode = HttpStatus.BAD_REQUEST.value(),
            isSuccess = false
        )
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(e: Exception): ApiError<Any> {
        log.error("Internal server error: ", e)
        return ApiError(
            message = "Internal server error",
            reason = e.message,
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            isSuccess = false
        )
    }

    @ExceptionHandler(com.lcaohoanq.ktservice.exceptions.InvalidApiPathVariableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleInvalidApiPathVariableException(e: com.lcaohoanq.ktservice.exceptions.InvalidApiPathVariableException): ApiError<Any> {
        return ApiError(
            message = "Invalid API path variable",
            reason = e.message,
            statusCode = HttpStatus.BAD_REQUEST.value(),
            isSuccess = false
        )
    }

    @ExceptionHandler(com.lcaohoanq.ktservice.exceptions.MethodArgumentNotValidException::class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationExceptions(
        ex: com.lcaohoanq.ktservice.exceptions.MethodArgumentNotValidException
    ): ResponseEntity<Map<String, String?>> {
        val errors: MutableMap<String, String?> = HashMap()
        ex.bindingResult.allErrors.forEach(Consumer { error: ObjectError ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.getDefaultMessage()
            errors[fieldName] = errorMessage
        })
        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(com.lcaohoanq.ktservice.exceptions.DeleteException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleDeleteException(e: com.lcaohoanq.ktservice.exceptions.DeleteException): ApiError<Any> {
        return ApiError(
            message = "Delete exception",
            reason = e.message,
            statusCode = HttpStatus.BAD_REQUEST.value(),
            isSuccess = false
        )
    }

    @ExceptionHandler(com.lcaohoanq.ktservice.exceptions.MalformDataException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMalformDataException(e: com.lcaohoanq.ktservice.exceptions.MalformDataException): ApiError<Any> {
        return ApiError(
            message = "Malformed data exception",
            reason = e.message,
            statusCode = HttpStatus.BAD_REQUEST.value(),
            isSuccess = false
        )
    }

    @ExceptionHandler(com.lcaohoanq.ktservice.exceptions.MalformBehaviourException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMalformBehaviourException(e: com.lcaohoanq.ktservice.exceptions.MalformBehaviourException): ApiError<Any> {
        return ApiError(
            message = "Malformed behaviour exception",
            reason = e.message,
            statusCode = HttpStatus.BAD_REQUEST.value(),
            isSuccess = false
        )
    }

    @ExceptionHandler(com.lcaohoanq.ktservice.exceptions.JwtAuthenticationException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    fun handleJwtAuthenticationException(
        ex: com.lcaohoanq.ktservice.exceptions.JwtAuthenticationException,
        request: WebRequest
    ): ResponseEntity<Any> {
        val body: MutableMap<String, Any?> = HashMap()
        body["timestamp"] = System.currentTimeMillis()
        body["status"] = HttpStatus.UNAUTHORIZED.value()
        body["error"] = "Unauthorized"
        body["message"] = ex.message
        body["path"] = (request as ServletWebRequest).request.requestURI
        return ResponseEntity(body, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(com.lcaohoanq.ktservice.exceptions.PermissionDeniedException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handlePermissionDeniedException(e: com.lcaohoanq.ktservice.exceptions.PermissionDeniedException): ApiError<Any> {
        return ApiError(
            message = "Permission denied",
            reason = e.message,
            statusCode = HttpStatus.FORBIDDEN.value(),
            isSuccess = false
        )
    }

    //    @ExceptionHandler(BadCredentialsException.class)
    //    @ResponseStatus(HttpStatus.BAD_REQUEST)
    //    public ApiError<Object> handleBadCredentialsException(BadCredentialsException e) {
    //        return ApiError.errorBuilder()
    //            .message("Bad credentials")
    //            .reason(e.getMessage())
    //            .statusCode(HttpStatus.BAD_REQUEST.value())
    //            .isSuccess(false)
    //            .build();
    //    }
    @ExceptionHandler(com.lcaohoanq.ktservice.exceptions.base.DataWrongFormatException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handlePasswordWrongFormatException(e: com.lcaohoanq.ktservice.exceptions.base.DataWrongFormatException): ApiError<Any> {
        return ApiError(
            message = "Data wrong format",
            reason = e.message,
            statusCode = HttpStatus.BAD_REQUEST.value(),
            isSuccess = false
        )
    }

    @ExceptionHandler(
        DataIntegrityViolationException::class,
        com.lcaohoanq.ktservice.exceptions.base.DataAlreadyExistException::class
    )
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleDataIntegrityViolationException(e: DataIntegrityViolationException): ApiError<Any> {
        return ApiError(
            message = "Data integrity violation",
            reason = e.message,
            statusCode = HttpStatus.CONFLICT.value(),
            isSuccess = false
        )
    }

    @ExceptionHandler(
        IOException::class
    )
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleIOException(e: IOException): ApiError<Any> {
        return ApiError(
            message = "IO exception",
            reason = e.message,
            statusCode = HttpStatus.NOT_FOUND.value(),
            isSuccess = false
        )
    }

    @ExceptionHandler(com.lcaohoanq.ktservice.exceptions.TooManyRequestsException::class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    fun handleTooManyRequestsException(e: com.lcaohoanq.ktservice.exceptions.TooManyRequestsException): ApiError<Any> {
        return ApiError(
            message = "Too many requests exception",
            reason = e.message,
            statusCode = HttpStatus.TOO_MANY_REQUESTS.value(),
            isSuccess = false
        )
    }
}

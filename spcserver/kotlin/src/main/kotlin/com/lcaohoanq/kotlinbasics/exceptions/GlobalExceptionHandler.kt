package com.lcaohoanq.kotlinbasics.exceptions

import com.lcaohoanq.kotlinbasics.api.ApiError
import com.lcaohoanq.kotlinbasics.exceptions.JwtAuthenticationException
import com.lcaohoanq.kotlinbasics.exceptions.base.DataAlreadyExistException
import com.lcaohoanq.kotlinbasics.exceptions.base.DataNotFoundException
import com.lcaohoanq.kotlinbasics.exceptions.base.DataWrongFormatException
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
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

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
class GlobalExceptionHandler {

    private val log = KotlinLogging.logger {}

    @ExceptionHandler(DataNotFoundException::class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun handleDataNotFoundException(e: DataNotFoundException): ApiError<Any> {
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

    @ExceptionHandler(InvalidApiPathVariableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleInvalidApiPathVariableException(e: InvalidApiPathVariableException): ApiError<Any> {
        return ApiError(
            message = "Invalid API path variable",
            reason = e.message,
            statusCode = HttpStatus.BAD_REQUEST.value(),
            isSuccess = false
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationExceptions(
        ex: MethodArgumentNotValidException
    ): ResponseEntity<Map<String, String?>> {
        val errors: MutableMap<String, String?> = HashMap()
        ex.bindingResult.allErrors.forEach(Consumer { error: ObjectError ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.getDefaultMessage()
            errors[fieldName] = errorMessage
        })
        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(DeleteException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleDeleteException(e: DeleteException): ApiError<Any> {
        return ApiError(
            message = "Delete exception",
            reason = e.message,
            statusCode = HttpStatus.BAD_REQUEST.value(),
            isSuccess = false
        )
    }

    @ExceptionHandler(MalformDataException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMalformDataException(e: MalformDataException): ApiError<Any> {
        return ApiError(
            message = "Malformed data exception",
            reason = e.message,
            statusCode = HttpStatus.BAD_REQUEST.value(),
            isSuccess = false
        )
    }

    @ExceptionHandler(MalformBehaviourException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMalformBehaviourException(e: MalformBehaviourException): ApiError<Any> {
        return ApiError(
            message = "Malformed behaviour exception",
            reason = e.message,
            statusCode = HttpStatus.BAD_REQUEST.value(),
            isSuccess = false
        )
    }

    @ExceptionHandler(JwtAuthenticationException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    fun handleJwtAuthenticationException(
        ex: JwtAuthenticationException,
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

    @ExceptionHandler(PermissionDeniedException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handlePermissionDeniedException(e: PermissionDeniedException): ApiError<Any> {
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
    @ExceptionHandler(DataWrongFormatException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handlePasswordWrongFormatException(e: DataWrongFormatException): ApiError<Any> {
        return ApiError(
            message = "Data wrong format",
            reason = e.message,
            statusCode = HttpStatus.BAD_REQUEST.value(),
            isSuccess = false
        )
    }

    @ExceptionHandler(
        DataIntegrityViolationException::class,
        DataAlreadyExistException::class
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

    @ExceptionHandler(TooManyRequestsException::class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    fun handleTooManyRequestsException(e: TooManyRequestsException): ApiError<Any> {
        return ApiError(
            message = "Too many requests exception",
            reason = e.message,
            statusCode = HttpStatus.TOO_MANY_REQUESTS.value(),
            isSuccess = false
        )
    }
}

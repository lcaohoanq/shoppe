package com.lcaohoanq.shoppe.exceptions;

import com.lcaohoanq.shoppe.components.LocalizationUtils;
import com.lcaohoanq.shoppe.dtos.responses.ExceptionResponse;
import com.lcaohoanq.shoppe.dtos.responses.base.ApiError;
import com.lcaohoanq.shoppe.dtos.responses.base.BaseResponse;
import com.lcaohoanq.shoppe.exceptions.base.DataAlreadyExistException;
import com.lcaohoanq.shoppe.exceptions.base.DataNotFoundException;
import com.lcaohoanq.shoppe.exceptions.base.DataWrongFormatException;
import com.lcaohoanq.shoppe.utils.MessageKey;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final LocalizationUtils localizationUtils;

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError<Object> handleDataNotFoundException(DataNotFoundException e) {
        return ApiError.errorBuilder()
            .message(localizationUtils.getLocalizedMessage(MessageKey.DATA_NOT_FOUND))
            .reason(e.getMessage())
            .statusCode(HttpStatus.NOT_FOUND.value())
            .isSuccess(false)
            .build();
    }

    @ExceptionHandler(MessagingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError<Object> handleMessagingException(MessagingException e) {
        return ApiError.errorBuilder()
            .message("Error sending email")
            .reason(e.getMessage())
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .isSuccess(false)
            .build();
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError<Object> handleNullPointerException(NullPointerException e) {
        return ApiError.errorBuilder()
            .message(localizationUtils.getLocalizedMessage(MessageKey.EXCEPTION_NULL_POINTER))
            .reason(e.getMessage())
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .isSuccess(false)
            .build();
    }

    @ExceptionHandler(BiddingRuleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError<Object> handleBiddingRuleException(BiddingRuleException e) {
        return ApiError.errorBuilder()
            .message(localizationUtils.getLocalizedMessage(MessageKey.BIDDING_RULE_EXCEPTION))
            .reason(e.getMessage())
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .isSuccess(false)
            .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError<Object> handleException(Exception e) {
        log.error("Internal server error: ", e);
        return ApiError.errorBuilder()
            .message(localizationUtils.getLocalizedMessage(MessageKey.INTERNAL_SERVER_ERROR))
            .reason(e.getMessage())
            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .isSuccess(false)
            .build();
    }

    @ExceptionHandler(GenerateDataException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse<Object> handleGenerateDataException(GenerateDataException e) {
        return ExceptionResponse.builder()
            .message(localizationUtils.getLocalizedMessage("exception.generate_data_error"))
            .reason(e.getMessage())
            .build();
    }

    @ExceptionHandler(InvalidApiPathVariableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError<Object> handleInvalidApiPathVariableException(
        InvalidApiPathVariableException e) {
        return ApiError.errorBuilder()
            .message(localizationUtils.getLocalizedMessage(
                MessageKey.EXCEPTION_INVALID_API_PATH_VARIABLE))
            .reason(e.getMessage())
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .isSuccess(false)
            .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError<Object> handleHttpMessageNotReadableException(
        HttpMessageNotReadableException ex) {
        return ApiError.errorBuilder()
            .message("Request body is required")
            .reason(ex.getMessage())
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .isSuccess(false)
            .build()
            ;
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
        MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DeleteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError<Object> handleDeleteException(DeleteException e) {
        return ApiError.errorBuilder()
            .message(localizationUtils.getLocalizedMessage(MessageKey.EXCEPTION_DELETE_ERROR))
            .reason(e.getMessage())
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .isSuccess(false)
            .build();
    }

    @ExceptionHandler(MalformDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError<Object> handleMalformDataException(MalformDataException e) {
        return ApiError.errorBuilder()
            .message(localizationUtils.getLocalizedMessage(MessageKey.EXCEPTION_MALFORMED_DATA))
            .reason(e.getMessage())
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .isSuccess(false)
            .build();
    }

    @ExceptionHandler(MalformBehaviourException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError<Object> handleMalformBehaviourException(MalformBehaviourException e) {
        return ApiError.errorBuilder()
            .message(
                localizationUtils.getLocalizedMessage(MessageKey.EXCEPTION_MALFORMED_BEHAVIOUR))
            .reason(e.getMessage())
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .isSuccess(false)
            .build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError<Object> handleAccessDeniedException(AccessDeniedException e,
                                                        WebRequest request) {
        return ApiError.errorBuilder()
            .message(localizationUtils.getLocalizedMessage(MessageKey.EXCEPTION_PERMISSION_DENIED))
            .reason("Insufficient privileges to access this resource")
            .statusCode(HttpStatus.FORBIDDEN.value())
            .isSuccess(false)
            .data(Map.of(
                "timestamp", System.currentTimeMillis(),
                "path", ((ServletWebRequest) request).getRequest().getRequestURI()
            ))
            .build();
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError<Object> handleJwtAuthenticationException(JwtAuthenticationException e,
                                                             WebRequest request) {
        return ApiError.errorBuilder()
            .message("Authentication Failed")
            .reason(e.getMessage())
            .statusCode(HttpStatus.UNAUTHORIZED.value())
            .isSuccess(false)
            .data(Map.of(
                "timestamp", System.currentTimeMillis(),
                "path", ((ServletWebRequest) request).getRequest().getRequestURI()
            ))
            .build();
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError<Object> handleExpiredJwtException(ExpiredJwtException e, WebRequest request) {
        return ApiError.errorBuilder()
            .message("Token Expired")
            .reason("The provided authentication token has expired")
            .statusCode(HttpStatus.UNAUTHORIZED.value())
            .isSuccess(false)
            .data(Map.of(
                "timestamp", System.currentTimeMillis(),
                "path", ((ServletWebRequest) request).getRequest().getRequestURI()
            ))
            .build();
    }

    @ExceptionHandler(KoiRevokeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError<Object> handleKoiRevokeException(KoiRevokeException e) {
        return ApiError.errorBuilder()
            .message(localizationUtils.getLocalizedMessage(MessageKey.EXCEPTION_KOI_REVOKE))
            .reason(e.getMessage())
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .isSuccess(false)
            .build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError<Object> handleBadCredentialsException(BadCredentialsException e) {
        return ApiError.errorBuilder()
            .message(localizationUtils.getLocalizedMessage(MessageKey.EXCEPTION_BAD_CREDENTIALS))
            .reason(e.getMessage())
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .isSuccess(false)
            .build();
    }

    @ExceptionHandler(FeedbackResponseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError<Object> handleFeedBackResponseException(FeedbackResponseException e) {
        return ApiError.errorBuilder()
            .message(localizationUtils.getLocalizedMessage(MessageKey.FEEDBACK_EXCEPTION))
            .reason(e.getMessage())
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .isSuccess(false)
            .build();
    }

    @ExceptionHandler(DataWrongFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError<Object> handlePasswordWrongFormatException(DataWrongFormatException e) {
        return ApiError.errorBuilder()
            .message(localizationUtils.getLocalizedMessage(MessageKey.WRONG_FORMAT))
            .reason(e.getMessage())
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .isSuccess(false)
            .build();
    }

    @ExceptionHandler(DataAlreadyExistException.class)
    public ResponseEntity<ApiError<Object>> handleDataAlreadyExistException(
        DataAlreadyExistException ex) {
        ApiError<Object> apiError = ApiError.errorBuilder()
            .message(ex.getMessage())
            .statusCode(HttpStatus.CONFLICT.value())
            .isSuccess(false)
            .data(Map.of(
                "timestamp", System.currentTimeMillis(),
                "path", Objects.requireNonNull(
                    ServletUriComponentsBuilder.fromCurrentRequest().build().getPath())
            ))
            .build();

        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    // Handle Spring's DataIntegrityViolationException separately
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError<Object>> handleDataIntegrityViolationException(
        DataIntegrityViolationException ex) {
        log.error("DataIntegrityViolationException: ", ex);
        ApiError<Object> apiError = ApiError.errorBuilder()
            .message("Data integrity violation: " + ex.getMostSpecificCause().getMessage())
            .statusCode(HttpStatus.CONFLICT.value())
            .isSuccess(false)
            .data(Map.of(
                "timestamp", System.currentTimeMillis(),
                "path", Objects.requireNonNull(
                    ServletUriComponentsBuilder.fromCurrentRequest().build().getPath())
            ))
            .build();

        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }
}

package com.lcaohoanq.commonspring.exceptions

import org.springframework.validation.BindingResult

class MethodArgumentNotValidException(val bindingResult: BindingResult) :
    RuntimeException("Validation failed")

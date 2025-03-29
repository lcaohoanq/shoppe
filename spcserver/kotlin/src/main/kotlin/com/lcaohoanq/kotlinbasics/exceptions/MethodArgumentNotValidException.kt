package com.lcaohoanq.kotlinbasics.exceptions

import lombok.Getter
import org.springframework.validation.BindingResult

@Getter
class MethodArgumentNotValidException(val bindingResult: BindingResult) :
    RuntimeException("Validation failed")

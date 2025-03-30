package com.lcaohoanq.ktservice.exceptions.base

import lombok.NoArgsConstructor

@NoArgsConstructor
open class DataNotFoundException(message: String?) : RuntimeException(message)

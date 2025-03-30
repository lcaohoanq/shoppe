package com.lcaohoanq.ktservice.exceptions.base

import lombok.NoArgsConstructor

@NoArgsConstructor
open class DataAlreadyExistException(message: String?) : RuntimeException(message)

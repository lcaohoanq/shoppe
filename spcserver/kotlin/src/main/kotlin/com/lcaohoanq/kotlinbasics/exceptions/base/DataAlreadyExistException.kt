package com.lcaohoanq.kotlinbasics.exceptions.base

import lombok.NoArgsConstructor

@NoArgsConstructor
open class DataAlreadyExistException(message: String?) : RuntimeException(message)

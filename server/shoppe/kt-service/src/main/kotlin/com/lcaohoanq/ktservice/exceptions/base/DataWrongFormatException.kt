package com.lcaohoanq.ktservice.exceptions.base

import lombok.NoArgsConstructor

@NoArgsConstructor
open class DataWrongFormatException(message: String?) : RuntimeException(message)

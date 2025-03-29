package com.lcaohoanq.kotlinbasics.exceptions

import lombok.NoArgsConstructor

@NoArgsConstructor
class GenerateDataException(message: String?) : RuntimeException(message)

package com.lcaohoanq.ktservice.exceptions

import lombok.NoArgsConstructor

@NoArgsConstructor
class GenerateDataException(message: String?) : RuntimeException(message)

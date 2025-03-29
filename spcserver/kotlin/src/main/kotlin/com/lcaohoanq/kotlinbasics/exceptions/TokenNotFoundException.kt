package com.lcaohoanq.kotlinbasics.exceptions

import com.lcaohoanq.kotlinbasics.exceptions.base.DataNotFoundException

class TokenNotFoundException(message: String?) : DataNotFoundException(message)

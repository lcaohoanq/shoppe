package com.lcaohoanq.kotlinbasics.exceptions

import com.lcaohoanq.kotlinbasics.exceptions.base.DataNotFoundException

class StaffNotFoundException(message: String?) : DataNotFoundException(message)

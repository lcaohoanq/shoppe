package com.lcaohoanq.kotlinbasics.exceptions

import com.lcaohoanq.kotlinbasics.exceptions.base.DataNotFoundException

class RoleNotFoundException(message: String?) : DataNotFoundException(message)

package com.lcaohoanq.kotlinbasics.exceptions

import com.lcaohoanq.kotlinbasics.exceptions.base.DataNotFoundException

class MemberNotFoundException(message: String?) : DataNotFoundException(message)

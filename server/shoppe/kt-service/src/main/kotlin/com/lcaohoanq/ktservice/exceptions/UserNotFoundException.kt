package com.lcaohoanq.ktservice.exceptions

import lombok.NoArgsConstructor

@NoArgsConstructor
class UserNotFoundException : RuntimeException {
    private val id: Long? = null
    private var email_phone: String? = null

    constructor(id: Long) : super("Could not find user $id")

    constructor(email_phone: String) : super("Could not find user$email_phone") {
        this.email_phone = email_phone
    }
}
